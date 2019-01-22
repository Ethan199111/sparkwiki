package scalalearn

import collection.{mutable => m}


object TwoSum extends App {
	val map = new m.HashMap[Int, Int]
	val arr = Array(5,4,3,2,1)
	val target = 8
	for (i <- arr.indices) {
		val gap = target - arr(i)
		if (map.contains(gap)) {
			println(arr(i), gap)
		} else {
			map.put(arr(i) , i)
		}
	}
}