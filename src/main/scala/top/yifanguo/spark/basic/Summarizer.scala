package top.yifanguo.spark.basic

import org.apache.spark.ml.linalg.Vectors
import top.yifanguo.spark.SparkUtil

object Summarizer {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		val data = Seq(
			(Vectors.dense(2.0, 3.0, 5.0), 1.0),
			(Vectors.dense(4.0, 6.0, 7.0), 2.0)
		)

		val df = spark.createDataFrame(data).toDF("features", "weight")

		val summarizer = df.summary()
		summarizer.foreach(println(_))

	}

}
