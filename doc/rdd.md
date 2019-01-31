[TOC]

# RDD 操作指南



# flatMap vs Map

 https://blog.csdn.net/shenshendeai/article/details/57081673



 # distinct 

distinct顾名思义就是把重复的元素去掉

```
scala> val rdd = sc.parallelize(Array(1,2,3,1,2,3))
rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:24

scala> rdd.getNumPartitions
res0: Int = 8

scala> rdd.distinct.collect()
res1: Array[Int] = Array(1, 2, 3)

scala>
```



# repartition vs coalesce

```
def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
  coalesce(numPartitions, shuffle = true)
}
```



```
If you are decreasing the number of partitions in this RDD, consider using `coalesce`,
* which can avoid performing a shuffle.
```

增大partitionNumber用repartition 缩小用coalesce



# sample



```
def sample(
    withReplacement: Boolean,
    fraction: Double,
    seed: Long = Utils.random.nextLong): RDD[T] = {
```



```
PoissonSampler 泊松分布 (true)
```

```
BernoulliSampler 伯努利
```



# randomSplit

把一个df分成训练集和测试集



# union

将两个rdd合并，如果需要unique就用distinct



> What's the difference between an RDD's map and mapPartitions method?

The method [map](https://spark.apache.org/docs/latest/api/python/pyspark.html?highlight=rdd#pyspark.RDD.map) converts each *element* of the source RDD into a single element of the result RDD by applying a function. [mapPartitions](https://spark.apache.org/docs/latest/api/python/pyspark.html?highlight=rdd#pyspark.RDD.mapPartitions) converts each *partition* of the source RDD into multiple elements of the result (possibly none).

> And does flatMap behave like map or like mapPartitions?

Neither, [flatMap](https://spark.apache.org/docs/latest/api/python/pyspark.html?highlight=rdd#pyspark.RDD.flatMap) works on a single element (as `map`) and produces multiple elements of the result (as `mapPartitions`).



# Map

Applies a transformation function on each item of the RDD and returns the result as a new RDD.

scala> val a = sc.parallelize(List("dog", "salmon", "salmon", "rat", "elephant"), 3)
a: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[6] at parallelize at <console>:24

scala> val b = a.map(_.length)
b: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[7] at map at <console>:25

scala> val c = a.zip(b)
c: org.apache.spark.rdd.RDD[(String, Int)] = ZippedPartitionsRDD2[8] at zip at <console>:27

scala> c.collect
res19: Array[(String, Int)] = Array((dog,3), (salmon,6), (salmon,6), (rat,3), (elephant,8))



# mapPartitions

This is a specialized map that is called only once for each partition. The entire content of the respective partitions is available as a sequential stream of values via the input argument (Iterarator[T]). The custom function must return yet another Iterator[U]. The combined result iterators are automatically converted into a new RDD. Please note, that the tuples (3,4) and (6,7) are missing from the following result due to the partitioning we chose.

`preservesPartitioning` indicates whether the input function preserves the partitioner, which should be `false` unless this is a pair RDD and the input function doesn't modify the keys.

**Listing Variants**

def mapPartitions[U: ClassTag](f: Iterator[T] => Iterator[U], preservesPartitioning: Boolean = false): RDD[U]



`mapPartitions` transformation is faster than `map` since it calls your function once/partition, not once/element



**Map** :

> 1. It processes one row at a time , very similar to map() method of MapReduce.
> 2. You return from the transformation after every row.

**MapPartitions**

> 1. It processes the complete partition in one go.
> 2. You can return from the function only once after processing the whole partition.
> 3. All intermediate results needs to be held in memory till you process the whole partition.
> 4. Provides you like setup() map() and cleanup() function of MapReduce
>
> `Map Vs mapPartitions` <http://bytepadding.com/big-data/spark/spark-map-vs-mappartitions/>
>
> `Spark Map` <http://bytepadding.com/big-data/spark/spark-map/>
>
> `Spark mapPartitions` <http://bytepadding.com/big-data/spark/spark-mappartitions/>



