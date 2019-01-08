package top.yifanguo.spark.featureengineering

import org.apache.spark.ml.feature.NGram
import top.yifanguo.spark.SparkUtil

object NgramDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		val wordDataFrame = spark.createDataFrame(Seq(
			(0, Array("Hi", "I", "heard", "about", "Spark")),
			(1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
			(2, Array("Logistic", "regression", "models", "are", "neat"))
		)).toDF("label", "words")

		wordDataFrame.show(false)

		val ngram = new NGram().setInputCol("words").setOutputCol("ngrams")
  		.setN(4)

		val ngramDataFrame = ngram.transform(wordDataFrame)
		ngramDataFrame.show(false)

		ngramDataFrame.take(3).map(_.getAs[Stream[String]]("ngrams").toList).foreach(println)
	}
}

/*
一个n-gram是一个包含n个tokens(如词)的序列。NGram可以将输入特征 转换为n-grams。
NGram输入一系列的序列,参数n用来决定每个n-gram的词个数。
输出包含一个n-grams序列,每个n-gram表示一个划定空间的连续词序列。
如果输入序列包含的词少于n,将不会有输出。
 */
