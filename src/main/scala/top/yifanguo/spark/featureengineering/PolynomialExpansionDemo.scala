package top.yifanguo.spark.featureengineering

import org.apache.spark.ml.feature.PolynomialExpansion
import org.apache.spark.ml.linalg.Vectors
import top.yifanguo.spark.SparkUtil

object PolynomialExpansionDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()
		val data = Array(
			Vectors.dense(-2.0, 2.3),
			Vectors.dense(0.0, 0.0),
			Vectors.dense(0.6, -1.1)
		)
		val df = spark.createDataFrame(data.map(Tuple1.apply))
			.toDF("features")
		val polynomialExpansion = new PolynomialExpansion()
			.setInputCol("features")
			.setOutputCol("polyFeatures")
			.setDegree(3)
		val polyDF = polynomialExpansion.transform(df)
		polyDF.show(false)
	}
}

/*
  Polynomial expansion是一个将特征展开到多元空间的处理过程。
 它通过n-degree结合原始的维度来定义。
 比如设置degree为2就可以将(x, y)转化为(x, x x, y, x y, y y)。
 */
