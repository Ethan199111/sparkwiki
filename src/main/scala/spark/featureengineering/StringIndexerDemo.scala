package spark.featureengineering

import org.apache.spark.ml.feature.{IndexToString, StringIndexer}
import spark.SparkUtil

object StringIndexerDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		val df = spark.createDataFrame(
			Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"),(6, "d"))
		).toDF("id", "category")

		val indexer = new StringIndexer()
			.setInputCol("category")
			.setOutputCol("categoryIndex")

		val indexed = indexer.fit(df).transform(df)
		indexed.show()


		val converter = new IndexToString()
			.setInputCol("categoryIndex")
			.setOutputCol("originalCategory")

		val converted = converter.transform(indexed)

		converted.show(false)
	}

}
