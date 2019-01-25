package scala.concurrency

object CollectionsBad extends App {
	import scala.collection._

	val buffer = mutable.ArrayBuffer[Int]()

	def add(numbers: Seq[Int]) = execute {
		buffer ++= numbers
		log(s"buffer = $buffer")
	}

	add(0 until 10)
	add(10 until 20)
}

object MiscSyncVars extends App {
	import scala.concurrent._
	val sv = new SyncVar[String]

	execute {
		Thread.sleep(500)
		log("sending a message")
		sv.put("This is secret.")
	}

	log(s"get  = ${sv.get}")
	log(s"take = ${sv.take()}")

	execute {
		Thread.sleep(500)
		log("sending another message")
		sv.put("Secrets should not be logged!")
	}

	log(s"take = ${sv.take()}")
	log(s"take = ${sv.take(timeout = 1000)}")
}

object CollectionsIterators extends App {
	import java.util.concurrent._

	val queue = new LinkedBlockingQueue[String]
	for (i <- 1 to 5500) queue.offer(i.toString)
	execute {
		val it = queue.iterator
		while (it.hasNext) log(it.next())
	}
	for (i <- 1 to 5500) queue.poll()
}

object CollectionsConcurrentMap extends App {
	import java.util.concurrent.ConcurrentHashMap

	import scala.collection.convert.decorateAsScala._

	val emails = new ConcurrentHashMap[String, List[String]]().asScala

	execute {
		emails("James Gosling") = List("james@javalove.com")
		log(s"emails = $emails")
	}

	execute {
		emails.putIfAbsent("Alexey Pajitnov", List("alexey@tetris.com"))
		log(s"emails = $emails")
	}

	execute {
		emails.putIfAbsent("Alexey Pajitnov", List("alexey@welltris.com"))
		log(s"emails = $emails")
	}

}

object CollectionsTrieMapBulk extends App {
	import scala.collection._

	val names = new concurrent.TrieMap[String, Int]
	names("Janice") = 0
	names("Jackie") = 0
	names("Jill") = 0

	execute {
		for (n <- 10 until 100) names(s"John $n") = n
	}

	execute {
		log("snapshot time!")
		for (n <- names.map(_._1).toSeq.sorted) log(s"name: $n")
	}

}