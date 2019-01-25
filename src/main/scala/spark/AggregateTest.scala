package spark

import org.apache.spark.sql.SparkSession

object AggregateTest {
	def main(args: Array[String]): Unit = {
		val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
		spark.sparkContext.setLogLevel("WARN")
		// 创建rdd，并分成6个分区
		val rdd = spark.sparkContext.parallelize(1 to 12).repartition(6)
		// 输出每个分区的内容
		rdd.mapPartitionsWithIndex((index:Int,it:Iterator[Int])=>{
			Array((s" $index : ${it.toList.mkString(",")}")).toIterator
		}).foreach(println)
		// 执行agg
		val res1 = rdd.aggregate(0)(seqOp, combOp)
	}
	// 分区内执行的方法，直接加和
	def seqOp(s1:Int, s2:Int):Int = {
		println("seq: "+s1+":"+s2)
		s1 + s2
	}
	// 在driver端汇总
	def combOp(c1: Int, c2: Int): Int = {
		println("comb: "+c1+":"+c2)
		c1 + c2
	}
}