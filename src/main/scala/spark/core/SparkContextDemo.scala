package spark.core

import spark.SparkUtil

object SparkContextDemo extends App {
	val spark = SparkUtil.startSpark()
	val sc = spark.sparkContext
	println(sc.sparkUser)
	println(sc.startTime)
	println(sc.applicationId)
	println(sc.appName)
	println(sc.defaultMinPartitions)
	println(sc.defaultParallelism)
	println(sc.deployMode)
	println(sc.master)
	println(sc.version)
	//broadcast
	val b = sc.broadcast(Array(1,2,3))
	println(b.value.mkString(","))
	val a = sc.accumulator(0, "Example Accumulator")

	sc.parallelize(1 to 10).foreach(x=> a += 1)

	println(a.value)
}