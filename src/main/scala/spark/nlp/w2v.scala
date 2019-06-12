package spark.nlp
import org.apache.spark.ml.feature.Word2Vec
import spark.SparkUtil
object w2v extends App {
	val spark = SparkUtil.startSpark()
	// Input data: Each row is a bag of words from a sentence or document.
	val documentDF = spark.createDataFrame(Seq(
		"Hi I heard about Spark".split(" "),
		"I wish Java could use case classes".split(" "),
		"Logistic regression models are neat".split(" "),
		"Logistic regression models are good".split(" ")
	).map(Tuple1.apply)).toDF("text")

	// Learn a mapping from words to Vectors.
	val word2Vec = new Word2Vec()
		.setInputCol("text")
		.setOutputCol("result")
		.setVectorSize(3)
		.setMinCount(0)
	val model = word2Vec.fit(documentDF)
	val result = model.transform(documentDF)
	//result.select("result").take(3).foreach(println)
	result.show(false)
}