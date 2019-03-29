package spark.basic

import org.apache.spark.sql.SparkSession

object WordCountDemo extends App {
	val spark = SparkSession.builder().master("local[*]").getOrCreate()
	val words = this.getClass.getResource("/beauty.txt").getPath
	val sc = spark.sparkContext
	import spark.implicits._
	val wordsCount = sc.textFile(words)
		.flatMap(line => line.split(" "))
		.map(word => (word, 1))
		.reduceByKey(_+_)
		.toDF("word", "count")
		.orderBy($"count".desc)
	wordsCount.show()
	val n = 7
	val topN = wordsCount.take(n).mkString(",")
	println(topN)
}
