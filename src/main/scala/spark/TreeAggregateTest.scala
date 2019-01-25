package spark

// https://cloud.tencent.com/developer/article/1172173

import org.apache.spark.sql.SparkSession

object TreeAggregateTest {
	def main(args: Array[String]): Unit = {
		val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
		spark.sparkContext.setLogLevel("WARN")

		val rdd = spark.sparkContext.parallelize(1 to 12).repartition(6)
		rdd.mapPartitionsWithIndex((index:Int,it:Iterator[Int])=>{
			Array(s" $index : ${it.toList.mkString(",")}").toIterator
		}).foreach(println)

		val res1 = rdd.treeAggregate(0)(seqOp, combOp)
		println(res1)
	}

	def seqOp(s1:Int, s2:Int):Int = {
		println("seq: "+s1+":"+s2)
		s1 + s2
	}

	def combOp(c1: Int, c2: Int): Int = {
		println("comb: "+c1+":"+c2)
		c1 + c2
	}
}