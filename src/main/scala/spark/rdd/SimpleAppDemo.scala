package spark.rdd

import spark.SparkUtil

case class feature(id:Int, category: String)

object SimpleAppDemo {

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

		import spark.implicits._
		val newDF = df.as[feature].collectAsList()
		newDF.toArray().foreach(println(_))
	}
}