package learnscala

import scala.actors.Actor
/**
	* 学习scala actor的基本操作
	* 和java中的Runnable Thread几乎一致
	*
	* 第一步：编写一个类，扩展特质trait Actor（scala 的actor）
	* 第二步：复写其中的act方法
	* 第三步：创建该actor的对象，调用该对象的start()方法，启动该线程
	* 第四步：通过scala的操作符"!"，发送消息
	* 第五步：结束的话，调用close即可
	*
	* 模拟单向打招呼
	*/
object ActorOps {
	def main(args: Array[String]): Unit = {
		val mFActor = new MyFirstActor()
		mFActor.start()
		// 发送消息
		mFActor ! "小美，睡了吗？"
		mFActor ! "我去洗澡了~"
		mFActor ! "呵呵"
	}
}

class MyFirstActor extends Actor {
	override def act(): Unit = {
		while(true) {
			receive {
				case str: String => println(str)
			}
		}
	}
}