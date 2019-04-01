package spark.core

import spark.SparkUtil

object SparkSessionDemo extends App {
	val spark = SparkUtil.startSpark()
	println(spark.conf.getAll.mkString("|"))
	import spark.implicits._
	val test = spark.createDataset(Array(1,2,3))
	test.show()
}
