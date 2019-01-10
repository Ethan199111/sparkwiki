package top.yifan.spark

// https://www.zybuluo.com/jewes/note/35032
//mapPartitions是map的一个变种。map的输入函数是应用于RDD中每个元素，
// 而mapPartitions的输入函数是应用于每个分区，
// 也就是把每个分区中的内容作为整体来处理的。
object SparkRDDDemo {


	def mapDoubleFunc(a : Int) : (Int,Int) = {
		(a, a * 2)
	}


	def doubleFunc(iter: Iterator[Int]) : Iterator[(Int,Int)] = {
		var res = List[(Int,Int)]()
		while (iter.hasNext)
		{
			val cur = iter.next
			res .::= (cur,cur*2)
		}
		res.iterator
	}

	def main(args: Array[String]): Unit = {

		val spark = SparkUtil.startSpark()
		val testData = Array(1, 2, 3, 4, 5)
		import spark.implicits._

		val testRDD = spark.sparkContext.parallelize(testData).repartition(2).toDF()


		val a = spark.sparkContext.parallelize(1 to 9, 3)

		a.foreach(println(_))

		val res = a.mapPartitions(doubleFunc)

		res.foreach(println(_))

		val s = a.reduce((x, y) => x)
		println(s)
	}

}
