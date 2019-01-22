package learnscala.rpc
import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.collection.mutable

class Master extends Actor{
	//保存worker  id
	val workerMessage=new mutable.HashMap[String,Long]
	//worker超时时间
	val WORKEER_TIMEOUT=10 * 1000
	override def preStart(): Unit ={
		//导入隐式转换，用于启动定时器
		import context.dispatcher
		//启动定时
		context.system.scheduler.schedule(0 millis,5000 millis,self,"CheckOfTimeOutWorker")
	}
	override def receive: Receive = {
		//注册woker把workerid 和当前时间存起来
		case  Workinfo(id)=>{
			if(!workerMessage.contains(id)) {
				workerMessage.put(id, System.currentTimeMillis())
			}
			println("注册worker:"+id)
			sender ! "MasterReply"
		}
		//接收worker心跳，更新接收时间
		case SendHearBeat(id)=>{
			workerMessage(id)=System.currentTimeMillis()
			println("接收到worker:"+id+"的心跳报告。")
		}
		//Master自己向自己发送的定期检查超时Worker的消息
		case  "CheckOfTimeOutWorker" =>{
			val currentTime = System.currentTimeMillis()
			//过滤出超时的worker
			val outTimeWorker=workerMessage.filter(m=>currentTime-m._2>WORKEER_TIMEOUT).toArray
			for(worker<-outTimeWorker){
				workerMessage.remove(worker._1)
			}
			println("woker数量："+outTimeWorker.size+"个，挂了")
		}
	}
}
object  Master{
	def main(args: Array[String]): Unit = {
		//接受参数
		val host="localhost"
		val port="8888"
		//配置信息
		val configStr=
			s"""
				 |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
				 |akka.remote.netty.tcp.hostname = "$host"
				 |akka.remote.netty.tcp.port = "$port"
         """.stripMargin
		val config=ConfigFactory.parseString(configStr)
		//ActorSystem,辅助创建和监控下面的Actor,单例
		val actorSystem=ActorSystem("MasterSystem",config)
		//执行mastor生命周期方法
		val master= actorSystem.actorOf(Props(new Master),"Master")
		actorSystem.whenTerminated
	}
}
