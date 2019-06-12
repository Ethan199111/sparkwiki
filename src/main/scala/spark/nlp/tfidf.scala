package spark.nlp
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import spark.SparkUtil

object tfidf extends App {

	val spark = SparkUtil.startSpark()

	val sentenceData = spark.createDataFrame(Seq(
		(0, "Hi I heard about Spark"),
		(0, "I wish Java could use case classes"),
		(1, "Logistic regression models are neat")
	)).toDF("label", "sentence")

	val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
	val wordsData = tokenizer.transform(sentenceData)
	val hashingTF = new HashingTF()
		.setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)
	val featurizedData = hashingTF.transform(wordsData)
	// alternatively, CountVectorizer can also be used to get term frequency vectors

	val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
	val idfModel = idf.fit(featurizedData)
	val rescaledData = idfModel.transform(featurizedData)
	rescaledData.select("features", "label").take(3).foreach(println)
}
