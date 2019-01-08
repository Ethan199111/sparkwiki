package top.yifanguo.spark

import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkUtil {

	// start a spark session
	def startSpark(): SparkSession = {
		SparkSession.builder().master("local[*]").getOrCreate()
	}

	// load data
	def loadData(sparkSession: SparkSession) : DataFrame = {
		sparkSession.read.format("libsvm").load("./data/agaricus.txt.train")

	}

	def loadLibsvm(sparkSession: SparkSession) : DataFrame = {
		sparkSession.read.format("libsvm").load("./data/libsvm")
	}

	def evaluate(trainPredict: DataFrame, testPredict: DataFrame): Unit = {
		val evaluator = new BinaryClassificationEvaluator()
			.setMetricName("areaUnderROC")
			.setLabelCol("label")
			.setRawPredictionCol("rawPrediction")

		val trainAuc = evaluator.evaluate(trainPredict)
		val testAuc = evaluator.evaluate(testPredict)

		println("Train set auc = " + trainAuc)
		println("Test set auc = " + testAuc)
	}
}