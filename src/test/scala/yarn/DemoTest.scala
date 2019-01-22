package yarn

import org.apache.hadoop.yarn.conf.YarnConfiguration
import org.apache.hadoop.yarn.server.MiniYARNCluster

object DemoTest {

	def main(args: Array[String]): Unit = {

		// 配置 minicluster
		val conf = new YarnConfiguration
		conf.set(YarnConfiguration.RM_ADDRESS, "localhost:8032")
		conf.set(YarnConfiguration.RM_HOSTNAME, "localhost")
		conf.set(YarnConfiguration.RM_SCHEDULER_ADDRESS, "localhost:8030")
		conf.set(YarnConfiguration.RM_RESOURCE_TRACKER_ADDRESS, "localhost:8031")
		conf.set(YarnConfiguration.RM_WEBAPP_ADDRESS, "localhost:8088")
		conf.setBoolean(YarnConfiguration.YARN_MINICLUSTER_FIXED_PORTS, true)
		val yrCluster = new MiniYARNCluster("test",
			1,
			1,
			1)
		yrCluster.init(conf)
		yrCluster.start()
	}
}
