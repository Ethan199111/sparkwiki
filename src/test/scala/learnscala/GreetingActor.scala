package learnscala

import scala.actors.Actor

object GreetingActor {
	def main(args: Array[String]): Unit = {
		val ga = new GreetingActor
		ga.start()

		ga ! Greeting("小美")
		ga ! WorkContent("装系统")
	}
}

case class Greeting(name:String)
case class WorkContent(content:String)

class GreetingActor extends Actor {
	override def act(): Unit = {
		while(true) {
			receive {
				case Greeting(name) => println(s"Hello, $name")
				case WorkContent(content) => println(s"Let's talk about sth. with $content")
			}
		}
	}
}