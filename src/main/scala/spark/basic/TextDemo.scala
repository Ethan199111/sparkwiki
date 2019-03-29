package spark.basic

import org.apache.spark.sql.SparkSession

object TextDemo extends App {
	val spark = SparkSession.builder()
		.master("local[*]")
		.config("spark.sql.warehouse.dir", "/tmp/spark-warehouse")
		.getOrCreate()
	val words = this.getClass.getResource("/words.txt").getPath
	val sc = spark.sparkContext
	import spark.implicits._
	val lines = sc.textFile(words).toDF("word")
	lines.show()
	println(lines.count())
	//val newLines = lines.map(r => r.getAs[String]("word") + "_happy")
	//newLines.show()
	val newLines = lines.map(r => r.get(0) + "_happy")
	newLines.show()
}