package learnscala.datastructure

import scala.collection.mutable

object mapDemo  extends  App {
	val personAges = mutable.HashMap("Alice"-> 20, "Job"->28, "Garry"->18)

	personAges.foreach(println(_))

	val meta = ("sa" -> 30)
	personAges += meta

	personAges.foreach(println(_))

	personAges -= "sa"
	personAges.foreach(println(_))

	val newPersonAges = personAges + ("Job" -> 10,"Fred" -> 7) // 更新过的新映射
	println("newPersonAges=> " + newPersonAges)

	personAges.foreach(me => println(me._1))
	personAges.foreach(me => println(me._2))

	val newp = for((k,v) <- personAges) yield(v, k)
	newp.foreach(println(_))


	val personAges2 = scala.collection.immutable.SortedMap("Alice"->10,"Fred"->7,"Bob"->3,"Cindy"->8)    // 会按照key的字典顺序进行排序
	println("personAges==> " + personAges2)  // personAges==> Map(Alice -> 10, Bob -> 3, Cindy -> 8, Fred -> 7)

	val months = scala.collection.mutable.LinkedHashMap("January" -> 1,"February" -> 2,"March" -> 3)    // 创建一个顺序的Map
	months += ("Fourth" -> 4)
	println("months=> " + months)   // months=> Map(January -> 1, February -> 2, March -> 3, Fourth -> 4)
}
