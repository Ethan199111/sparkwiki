package top.yifan.spark.rdd

import top.yifan.spark.SparkUtil

object SampleDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		val df = spark.createDataFrame(Seq(
			(0, "a"),
			(1, "b"),
			(2, "c"),
			(3, "a"),
			(4, "a"),
			(5, "c")
		)).toDF("id", "category")

		df.show(false)
		df.printSchema()

		var i = 0
		while (i < 1) {
			df.rdd.sample(false, 0.5).foreach(print(_))
			i+=1
		}
	}
}