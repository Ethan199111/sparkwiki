package scala

import org.apache.hadoop.fs.Path
import org.apache.hadoop.yarn.api.ApplicationConstants

object StringDemo extends App {
	val cmd = new StringBuilder
	val javaCommand = "\"" + ApplicationConstants.Environment.JAVA_HOME.$() + "/bin/java\""
	val blank = " "
	cmd.append(javaCommand)
		.append(blank)
		.append("amMain")
		.append(blank)
	val stdout = "1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR +
		Path.SEPARATOR + ApplicationConstants.STDOUT
	val stderr = "2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR +
		Path.SEPARATOR + ApplicationConstants.STDERR
	cmd.append(stdout)
	cmd.append(blank)
	cmd.append(stderr)
	println(cmd.toString())

}
