package yarn

import java.io.{File, IOException}
import java.nio.ByteBuffer
import java.util.Collections

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.permission.FsPermission
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.DataOutputBuffer
import org.apache.hadoop.security.{Credentials, UserGroupInformation}
import org.apache.hadoop.util.ClassUtil
import org.apache.hadoop.yarn.api.ApplicationConstants
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse
import org.apache.hadoop.yarn.api.records._
import org.apache.hadoop.yarn.client.api.YarnClient
import org.apache.hadoop.yarn.conf.YarnConfiguration
import org.apache.hadoop.yarn.util.{ConverterUtils, Records}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.collection.mutable

class Client(conf: Configuration) {
	private val log = LoggerFactory.getLogger(this.getClass)
	private val yarnClient = YarnClient.createYarnClient
	private val amMemory = 512
	private val amCores = 1
	private val executorMemory = 1024
	private val executorCores = 1
	private val amMain = classOf[ApplicationMaster].getName
	private val amJar = ClassUtil.findContainingJar(classOf[ApplicationMaster])
	private val fs = FileSystem.get(conf)
	private var appMasterJar = ""

	def setAppMasterJar(jar: String): Unit = {
		appMasterJar = jar
	}

	private def ascSetResource(): Resource = {
		val amResource = Records.newRecord(classOf[Resource])
		amResource.setMemory(amMemory)
		amResource.setVirtualCores(amCores)
		amResource
	}

	def submitApplication() : ApplicationId = {
		var appId: ApplicationId = null

		// 初始化
		yarnClient.init(conf)
		// 启动yarnClient
		yarnClient.start()

		log.info("Requesting a new application from cluster with %d NodeManagers"
			.format(yarnClient.getYarnClusterMetrics.getNumNodeManagers))

		// 1 创建一个新的app然后拿到appResponse和Id
		val newApp = yarnClient.createApplication()
		val newAppResponse = newApp.getNewApplicationResponse
		appId = newAppResponse.getApplicationId

		// 2 验证集群资源能力
		verifyClusterResources(newAppResponse)

		// 3 启动AM
		val clc = createContainerLaunchContext(newAppResponse)
		val asc = newApp.getApplicationSubmissionContext
		asc.setResource(ascSetResource())
		asc.setAMContainerSpec(clc)

		// 4. 提交任务
		yarnClient.submitApplication(asc)
	}

	private def verifyClusterResources(newAppResponse: GetNewApplicationResponse): Unit = {
		val maxMem = newAppResponse.getMaximumResourceCapability.getMemory
		if (executorMemory > maxMem) {
			throw new IllegalArgumentException(s"Required executor memory ($executorMemory)," +
				s"the max threshold ($maxMem MB) of this cluster! Please check the values of " +
				s"'yarn.scheduler.maximum-allocation-mb' and/or 'yarn.nodemanager.resource.memory-mb'.")
		}
		if (amMemory > maxMem) {
			throw new IllegalArgumentException(s"Required AM memory $amMemory " +
				s"is above the max threshold ($maxMem MB) of this cluster! " +
				"Please check the values of 'yarn.scheduler.maximum-allocation-mb' and/or " +
				"'yarn.nodemanager.resource.memory-mb'.")
		}
	}

	/**
		* Set up a ContainerLaunchContext to launch our ApplicationMaster container.
		* This sets up the launch environment, java options, and the command for launching the AM.
		*/
	private def createContainerLaunchContext(newAppResponse: GetNewApplicationResponse)
	: ContainerLaunchContext = {
		log.info("Setting up container launch context for our AM")
		// 创建一个clc
		val clc = Records.newRecord(classOf[ContainerLaunchContext])
		// 然后set我们需要的fields
		clc.setCommands(Collections.singletonList(clcSetCommands)) // am里要执行的命令
		clc.setLocalResources(clcSetLocalResources(newAppResponse).asJava) // 需要的本地资源
		clc.setEnvironment(clcSetEnvironment.asJava) // am里运行所需的环境变量
		clc.setTokens(clcSetTokens) // 安全验证tokens
		clc
	}

	// "$JAVA_HOME/bin/java" amMain 1><LOG_DIR>/stdout 2><LOG_DIR>/stderr
	private def clcSetCommands: String = {
		val cmd = new StringBuilder
		val javaCommand = "\"" + ApplicationConstants.Environment.JAVA_HOME.$() + "/bin/java\""
		val blank = " "
		cmd.append(javaCommand)
  		.append(blank)
  		.append(amMain)
  		.append(blank)
		val stdout = "1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR +
			Path.SEPARATOR + ApplicationConstants.STDOUT
		val stderr = "2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR +
			Path.SEPARATOR + ApplicationConstants.STDERR
		cmd.append(stdout)
		cmd.append(blank)
		cmd.append(stderr)
		cmd.toString()
	}

	private def clcSetLocalResources(appResponse: GetNewApplicationResponse): mutable.HashMap[String, LocalResource] = {
		val localResourceMap = new mutable.HashMap[String, LocalResource]()
		val amJarFile = new File(amJar)
		val lr = toLocalResource(fs, appResponse.getApplicationId.toString, amJarFile)
		localResourceMap.put(amJarFile.getName, lr)
		localResourceMap
	}

	private def clcSetEnvironment : mutable.HashMap[String, String] = {
		val envMap = new mutable.HashMap[String, String]()
		envMap.put("CLASSPATH", hadoopClassPath)
		envMap.put("LANG", "en_US.UTF-8")
		envMap
	}

	private def clcSetTokens: ByteBuffer = {
		if (UserGroupInformation.isSecurityEnabled) {
			// Note: Credentials class is marked as LimitedPrivate for HDFS and MapReduce
			val credentials = new Credentials()
			val tokenRenewer = conf.get(YarnConfiguration.RM_PRINCIPAL)
			if (tokenRenewer == null || tokenRenewer.length == 0) {
				throw new IOException(
					"Can't get Master Kerberos principal for the RM to use as renewer")
			}
			// For now, only getting tokens for the default file-system
			val tokens = fs.addDelegationTokens(tokenRenewer, credentials)
			if (tokens != null) {
				for (token <- tokens) {
					log.info("Got dt for " + fs.getUri + "; " + token)
				}
			}
			val dob = new DataOutputBuffer
			credentials.writeTokenStorageToStream(dob)
			val fsTokens = ByteBuffer.wrap(dob.getData, 0, dob.getLength)
			fsTokens
		}
		null
	}

	private def copyLocalFilesToHdfs(fs: FileSystem, appId: String, srcFilePath: String): Path = {
		// 本地路径
		val src = new Path(srcFilePath)
		// hdfs上路径
		val suffix = ".staging" + File.separator + appId + File.separator + src.getName
		val dst = new Path(fs.getHomeDirectory, suffix)
		// http://permissions-calculator.org/decode/0755/
		if (!fs.exists(dst.getParent)) {
			FileSystem.mkdirs(
				fs,
				dst.getParent,
				FsPermission.createImmutable(Integer.parseInt("755", 8).toShort))
		}
		// 把本地的src文件拷贝到dst上
		fs.copyFromLocalFile(src, dst)
		dst
	}

	private def toLocalResource(fs: FileSystem, appId: String, file: File): LocalResource = {
		val hdfsFile = copyLocalFilesToHdfs(fs, appId, file.getPath)
		val stat = fs.getFileStatus(hdfsFile)
		val res = LocalResource.newInstance(
			ConverterUtils.getYarnUrlFromURI(hdfsFile.toUri),
			LocalResourceType.FILE,
			LocalResourceVisibility.PRIVATE,
			stat.getLen,
			stat.getModificationTime)
		res
	}

	private def hadoopClassPath : String = {
		val classPathEnv = new StringBuilder()
		classPathEnv.append(File.pathSeparatorChar).append("./*")
		val classPaths = YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH
		classPaths.foreach(i =>
			classPathEnv.append(File.pathSeparatorChar).append(i.trim)
		)
		if (conf.getBoolean(YarnConfiguration.IS_MINI_YARN_CLUSTER, false)) {
			classPathEnv.append(File.pathSeparatorChar)
			classPathEnv.append(System.getProperty("java.class.path"))
		}
		classPathEnv.toString()
	}
}