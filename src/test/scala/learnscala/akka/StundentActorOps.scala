package learnscala.akka

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import learnscala.MessageProtocol.{InitSign, QuoteRequest, QuoteResponse}

import scala.util.Random

/**
	* 基于AKKA Actor的单向通信案例
	* 学生向老师发送请求

object _01StudentActorOps {
	def main(args: Array[String]): Unit = {
		// 第一步：构建Actor操作系统
		val actorSystem = ActorSystem("StudentActorSystem")
		// 第二步：actorSystem创建TeacherActor的代理对象ActorRef
		val teacherActorRef = actorSystem.actorOf(Props[TeacherActor])
		// 第三步：发送消息
		teacherActorRef ! QuoteRequest()

		Thread.sleep(2000)
		// 第四步：关闭
		actorSystem.terminate()
	}
}
*/

object DriverApp {
	def main(args: Array[String]): Unit = {
		val actorSystem = ActorSystem("teacherStudentSystem")
		// 老师的代理对象
		val teacherActorRef = actorSystem.actorOf(Props[TeacherActor], "teacherActor")
		// 学生的代理对象
		val studentActorRef = actorSystem.actorOf(Props[StudentActor](new StudentActor(teacherActorRef)), "studentActor")

		studentActorRef ! InitSign

		Thread.sleep(2000)

		actorSystem.terminate()
	}
}



class TeacherActor extends Actor {
	val quotes = List(
		"Moderation is for cowards",
		"Anything worth doing is worth overdoing",
		"The trouble is you think you have time",
		"You never gonna know if you never even try")

	override def receive = {
		case QuoteRequest() => {
			val random = new Random()

			val randomIndex = random.nextInt(quotes.size)
			val randomQuote = quotes(randomIndex)

			val response = QuoteResponse(randomQuote)
			// println(response)
			sender ! response
		}
	}
}


class StudentActor(teacherActorRef:ActorRef) extends Actor with ActorLogging {
	override def receive = {
		case InitSign => {
			teacherActorRef ! QuoteRequest()
		}
		case QuoteResponse(resp) => {
			log.info(s"$resp")
		}
	}
}



