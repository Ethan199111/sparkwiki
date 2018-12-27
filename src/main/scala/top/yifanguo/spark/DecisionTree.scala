package top.yifanguo.spark

import org.apache.spark.ml.classification.DecisionTreeClassifier

object DecisionTree {

	def main(args: Array[String]): Unit = {

		val spark = SparkUtil.startSpark()
		val data= SparkUtil.loadData(spark)

		val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

		// Train a DecisionTree model.
		val dt = new DecisionTreeClassifier()

		val model = dt.fit(trainingData)

		val train_predict = model.transform(trainingData)
		val test_predict = model.transform(testData)
		SparkUtil.evaluate(train_predict,test_predict)
	}

}