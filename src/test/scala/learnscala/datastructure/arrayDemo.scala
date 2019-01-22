package learnscala.datastructure

import scala.collection.mutable.ArrayBuffer

object arrayDemo extends App {
	val numsArray = new Array[Int](30)
	println(numsArray.length)

	val test = Array(2, 3, 4, 5, 6)
	println(test.mkString(","))

	val array = Array.fill(2)(math.random)
	println(array.mkString(","))

	val array2 = Array.fill(5)(3.5)
	println(array2.mkString(","))

	val ab = new ArrayBuffer[Int]()

	// 增
	ab += 1
	println(ab)
	ab.append(2)
	println(ab)
	ab += (3, 4, 5)
	println(ab)
	ab ++= Array(6, 7)
	println(ab)
	// insert
	ab.insert(3, -1, -2)    // 可以在某一个位置插入多个元素
	println(ab)

	// 删
	ab.trimEnd(1)   // 删除数组末尾的1个元素
	println(ab)
	ab.remove(3, 1) // 从索引位置3开始删除，删除2个元素
	println(ab)

	// 改
	ab(3) = -3
	println(ab)

	// 查
	println("==============================")
	for(i <- ab if (i != 2) && (i != -3)) {
		println(i)
	}

	val arr = for(i <- ab if (i != 2) && (i != -3)) yield i * 2

	println("arr:" + arr)

	println("通配符：")
	arr.filter( _ > 0).map{ 2 * _}.foreach(println(_))

	println("max=" + arr.max)


	val array3 = Array.fill(5)(math.random)
	array3.sorted.foreach(print(_))

	println()

	val array_2d = Array.ofDim[Double](2,3)
	for (a <- array_2d) println(a.toList)


}
