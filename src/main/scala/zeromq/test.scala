package zeromq

import org.zeromq.{ZContext, ZMQ}

object test extends App {
	// 创建一个用于I/O线程的context
	val context = new ZContext(1)
	// server端
	val socket = context.createSocket(ZMQ.REP)
	//bind
	socket.bind("tcp://127.0.0.1:5555")

	while(!Thread.currentThread().isInterrupted) {
		val reply = socket.recv(0)
		println("Receive: " + new String(reply, ZMQ.CHARSET))
		val response = "hello world!"
		socket.send(response.getBytes(ZMQ.CHARSET), 0)
	}
	socket.close();  //先关闭socket
	context.close();  //关闭当前的上下文
}