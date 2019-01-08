package top.yifanguo.spark.treemodel

import org.apache.spark.ml.classification.GBTClassifier
import top.yifanguo.spark.SparkUtil

object GBDTDemo {

	def main(args: Array[String]): Unit = {

		val spark = SparkUtil.startSpark()
		val data= SparkUtil.loadData(spark)

		val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

		// Train a DecisionTree model.
		val dt = new GBTClassifier()
			.setMaxBins(32)
			.setMaxDepth(5)

		val model = dt.fit(trainingData)

		val train_predict = model.transform(trainingData)
		val test_predict = model.transform(testData)

		SparkUtil.evaluate(train_predict,test_predict)

	}

}
