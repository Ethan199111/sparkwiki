package spark

import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier

object XGBoost {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()
		val data= SparkUtil.loadData(spark)
		val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
		// Train a DecisionTree model.
		val dt = new XGBoostClassifier()
		val model = dt.fit(trainingData)
		val train_predict = model.transform(trainingData)
		val test_predict = model.transform(testData)
		train_predict.show(1, false)
		//SparkUtil.evaluate(train_predict,test_predict)
	}

}