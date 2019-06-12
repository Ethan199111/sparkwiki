package spark.nlp

import org.apache.spark.ml.feature.FeatureHasher
import spark.SparkUtil

object FeatureHasherDemo extends App {

	val spark = SparkUtil.startSpark()
	import spark.implicits._

	val df = Seq(
		(2.0, true, "1", "foo"),
		(3.0, false, "2", "bar"),
		(1.0, true, "10", "foobar")
	).toDF("real", "bool", "stringNum", "string")

	df.show(false)

	val hasher = new FeatureHasher()
  	.setInputCols("real", "bool", "stringNum", "string")
  	.setOutputCol("features")

	hasher.transform(df).show(false)

}
