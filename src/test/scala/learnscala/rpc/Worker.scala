package learnscala.rpc

import java.util.UUID
import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import scala.concurrent.duration._
import com.typesafe.config.ConfigFactory
class Worker(val masterHost:String,val masterPort:String) extends Actor{
	var master:ActorSelection=_
	//生成uuid作为worker标识
	val id=UUID.randomUUID().toString
	//建立连接，链接Master
	override def preStart(): Unit ={
		//Master就是你前面给master起的名字
		master=context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
		//id和其他信息封装到一个类中，把这个类发送过去，注意这个类要序列化
		master ! Workinfo(id)
	}
	override def receive: Receive = {
		//接收master返回来的信息后启动定时
		case "MasterReply"=>{
			println("a reply from master")
			//导入隐式转换，用于启动定时器
			import context.dispatcher
			//启动定时任务，通过case回调SendHearBeat向master发送心跳(id标识)
			context.system.scheduler.schedule(0 millis,5000 millis,self,"SendHeartBeat")
		}
		case  "SendHearBeat"=>{
			println("worker send heartbeat")
			master ! SendHearBeat(id)
		}
	}
}
object Worker{
	def main(args: Array[String]): Unit = {
		//接受参数
		val workerHost="localhost"
		val workerPort="9999"
		val masterHost="localhost"
		val masterPort="8888"
		//配置信息
		val configStr=
			s"""
				 |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
				 |akka.remote.netty.tcp.hostname = "$workerHost"
				 |akka.remote.netty.tcp.port = "$workerPort"
         """.stripMargin
		val config=ConfigFactory.parseString(configStr)
		//ActorSystem,辅助创建和监控下面的Actor,单例
		val actorSystem=ActorSystem("WorkerSystem",config)
		//启动Actor，woker会被实例化，生命周期方法会被调用
		actorSystem.actorOf(Props(new Worker(masterHost,masterPort)),"Worker")
	}
}
