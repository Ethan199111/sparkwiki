package spark.core

import spark.SparkUtil

object SparkConfDemo extends App {
	val spark = SparkUtil.startSpark()
	val sc = spark.sparkContext
	println(sc.getConf.getAll.mkString("|"))
}