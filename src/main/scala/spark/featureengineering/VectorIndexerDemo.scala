package spark.featureengineering

import org.apache.spark.ml.feature.VectorIndexer
import spark.SparkUtil

object VectorIndexerDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		val data = spark.read.format("libsvm").load(
			"./data/libsvm")

		val indexer = new VectorIndexer()
			.setInputCol("features")
			.setOutputCol("indexed")
			.setMaxCategories(10)

		val indexerModel = indexer.fit(data)

		val categoricalFeatures: Set[Int] = indexerModel.categoryMaps.keys.toSet
		println(s"Chose ${categoricalFeatures.size} " +
			s"categorical features: ${categoricalFeatures.mkString(", ")}")

		// Create new column "indexed" with categorical values transformed to indices
		val indexedData = indexerModel.transform(data)
		indexedData.show(false)
	}

}
