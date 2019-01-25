package yarn


/*
class ApplicationMaster {

}
*/
/*
object ApplicationMaster {

	def main(args: Array[String]): Unit = {
		val conf = new YarnConfiguration
		var isDebug = false
		if (args.length >= 1 && args(0).equalsIgnoreCase("debug")) isDebug = true
		if (isDebug) {
			conf.set(YarnConfiguration.RM_ADDRESS, "localhost:8032")
			conf.set(YarnConfiguration.RM_HOSTNAME, "localhost")
			conf.set(YarnConfiguration.RM_SCHEDULER_ADDRESS, "localhost:8030")
			conf.set(YarnConfiguration.RM_RESOURCE_TRACKER_ADDRESS, "localhost:8031")
			conf.set(YarnConfiguration.RM_WEBAPP_ADDRESS, "localhost:8088")
			conf.setBoolean(YarnConfiguration.YARN_MINICLUSTER_FIXED_PORTS, true)
		}
		val allocListener = new AMRMClientAsync.CallbackHandler {
			override def onContainersCompleted(statuses: util.List[ContainerStatus]): Unit = ???
			override def onContainersAllocated(containers: util.List[Container]): Unit = ???
			override def onShutdownRequest(): Unit = ???
			override def onNodesUpdated(updatedNodes: util.List[NodeReport]): Unit = ???
			override def getProgress: Float = ???
			override def onError(e: Throwable): Unit = ???
		}
		val rm = AMRMClientAsync.createAMRMClientAsync(1000, allocListener)
		rm.init(conf)
		rm.start()
		val registration = rm.registerApplicationMaster(
			"",
			-1,
			"")

		while ( {
			true
		}) {
			System.out.println("(stdout)Hello World")
			System.err.println("(stderr)Hello World")
			Thread.sleep(1000)
		}
	}
}
*/