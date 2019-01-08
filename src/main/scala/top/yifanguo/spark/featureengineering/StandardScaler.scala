package top.yifanguo.spark.featureengineering

import org.apache.spark.ml.feature.StandardScaler
import top.yifanguo.spark.SparkUtil

object StandardScaler {

	def main(args: Array[String]): Unit = {


		val spark = SparkUtil.startSpark()

		val dataFrame = SparkUtil.loadLibsvm(spark)

		//dataFrame.show()

		val scaler = new StandardScaler()
			.setInputCol("features")
			.setOutputCol("scaledFeatures")
			.setWithStd(true)
			.setWithMean(false)

		// Compute summary statistics by fitting the StandardScaler.
		val scalerModel = scaler.fit(dataFrame)

		// Normalize each feature to have unit standard deviation.
		val scaledData = scalerModel.transform(dataFrame)
		scaledData.show(1,false)
	}

}