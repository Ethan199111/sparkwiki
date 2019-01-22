package run

import org.apache.spark.ml.classification.{LogisticRegression, VLogisticRegression}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import top.yifan.spark.SparkUtil

object LogisticRegression extends App {

	val spark = SparkUtil.startSpark()

	val path = "./data/agaricus.txt.train"

	val df = spark.read.format("libsvm").load(path)

	//df.show()

	val lr = new LogisticRegression()
  	.setMaxIter(10)

	val nlr = new VLogisticRegression()
  	.setMaxIter(10)

	val mlr = lr.fit(df)
	val nmlr = nlr.fit(df)

	val predict = mlr.transform(df)
	val n_predict = nmlr.transform(df)

	predict.show()
	n_predict.show()

	val evaluator = new BinaryClassificationEvaluator()
		.setMetricName("areaUnderROC")
		.setLabelCol("label")
		.setRawPredictionCol("rawPrediction")

	val auc = evaluator.evaluate(predict)
	val n_auc = evaluator.evaluate(n_predict)

	println("auc is: " + auc)
	println("n_auc is: " + n_auc)

}
