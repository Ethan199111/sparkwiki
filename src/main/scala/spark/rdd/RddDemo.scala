package spark.rdd

import spark.SparkUtil


// demo for all rdd api
object RddDemo extends App {

	val spark = SparkUtil.startSpark()
	val sc = spark.sparkContext
	val data = SparkUtil.loadData(spark).repartition(10)

	data.show()
	println(data.rdd.getNumPartitions)


	val partitions = data.rdd.partitions

	//println(partitions.toString)


	val rdd = sc.parallelize(List("coffee panda","happy panda","happiest panda party"))
	rdd.map(x=>x).collect

	val rdd1 = sc.parallelize(List(1,2,3,3,4,4,5,1,2,3,4,5,6,7,8,9,1))
	rdd1.map(x=>x+1).collect

	// latMap说明白就是先map然后再flat，再来看个例子
	rdd1.flatMap(x=>x.to(3)).collect

	// https://blog.csdn.net/WYpersist/article/details/80220211
	val res = rdd1.mapPartitions { iter=>
		val instances = iter.toArray
		instances.sliding(2, 2).map { batch =>
			println(batch.mkString(","))
		}
	}.collect()


	rdd1.mapPartitionsWithIndex {
		(partid, iter) => {
			var part_map = scala.collection.mutable.Map[String, List[Int]]()
			var part_name = "part_" + partid
			part_map(part_name) = List[Int]()
			while (iter.hasNext) {
				part_map(part_name) :+= iter.next() //:+= 列表尾部追加元素
			}
			part_map.iterator
		}
	}.collect()
}