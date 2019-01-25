package zeromq

import org.zeromq.ZMQ

object client extends App {
	val context = ZMQ.context(1)
	val socket = context.socket(ZMQ.REQ)

	socket.connect("tcp://127.0.0.1:5555")


	while(!Thread.currentThread().isInterrupted) {
		val rs = Console.readLine()

		socket.send(rs, 0)
		Thread.sleep(100)
		val reply = socket.recv(0)

		println("Client received:" + new String(reply, ZMQ.CHARSET))
	}
}
