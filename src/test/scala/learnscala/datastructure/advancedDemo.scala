package learnscala.datastructure

object advancedDemo extends App {
	(1 to 9).map(0.1 * _).foreach(println(_))

	(1 to 9).filter(_ % 2 ==0).foreach(println)

	(1 to 9).sortWith(_ > _).foreach(println)

	val names = List("Peter","Paul","Mary")

	names.map(_.toUpperCase).foreach(println(_))


	def swithOps: Unit ={
		var sign = 0
		val ch: Char = '+'
		ch match {
			case '+' => sign = 1
			case '-' => sign = -1
			case _ => sign = 0
		}
		println("sign===> " + sign)
	}

}
