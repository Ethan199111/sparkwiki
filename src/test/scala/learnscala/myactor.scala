package learnscala

import scala.actors.Actor

/**
	* actor之线程间，互相通信
	*
	* studentActor
	*     向老师问了一个问题
	*
	* teacherActor
	*     向学生做回应
	*
	* 通信的协议：
	* 请求，使用Request(内容)来表示
	* 响应，使用Response(内容)来表示
	*
	* 消息的同步和Future
	* 1、Scala在默认情况下，消息都是以异步进行发送的；但是如果发送的消息是同步的，即对方接受后，一定要给自己返回结果，那么可以使用!?的方式发送消息。即：
	*
	* val response= activeActor !? activeMessage
	* 2、如果要异步发送一个消息，但是在后续要获得消息的返回值，那么可以使用Future。即!!语法，如下：
	*
	* val futureResponse = activeActor !! activeMessage
	* val activeReply = future()
	*/
object _03CommunicationActorOps {
	def main(args: Array[String]): Unit = {
		val teacherActor = new TeacherActor()
		teacherActor.start()
		val studentActor = new StudentActor(teacherActor)
		studentActor.start()

		studentActor ! Request("老李啊，scala学习为什么这么难啊")
	}
}

case class Request(req:String)
case class Response(resp:String)

class StudentActor(teacherActor: TeacherActor) extends Actor {
	override def act(): Unit = {
		while(true) {
			receive {
				case Request(req) => {
					// 向老师请求相关的问题
					println("学生向老师说：" + req)
					teacherActor ! Request(req)
				}
				case Response(resp) => {
					println(resp)
					println("高！")
				}
			}
		}
	}
}

class TeacherActor() extends Actor {
	override def act(): Unit = {
		while (true) {
			receive {
				case Request(req) => {  // 接收到学生的请求
					sender ! Response("这个问题，需要如此搞定~")
				}
			}
		}
	}
}