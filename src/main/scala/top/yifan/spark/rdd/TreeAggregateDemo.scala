package top.yifan.spark.rdd

import top.yifan.spark.SparkUtil

object TreeAggregateDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()

		def seqOp(s1:Int, s2:Int):Int = {
			println("seq: "+s1+":"+s2)
			s1 + s2
		}

		def combOp(c1: Int, c2: Int): Int = {
			println("comb: "+c1+":"+c2)
			c1 + c2
		}

		val rdd = spark.sparkContext.parallelize(1 to 12).repartition(6)
		//val res1 = rdd.aggregate(0)(seqOp, combOp)
		val res2 = rdd.treeAggregate(0)(seqOp, combOp)
		//println(res1)
		println(res2)
	}
}