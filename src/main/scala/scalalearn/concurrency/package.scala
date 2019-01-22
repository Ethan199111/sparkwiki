package scalalearn

import scala.concurrent.ExecutionContext

package object concurrency {

	def log(msg: String) {
		println(s"${Thread.currentThread.getName}: $msg")
	}

	def thread(body: =>Unit): Thread = {
		val t = new Thread {
			override def run():Unit = body
		}
		t.start()
		t
	}
	def execute(body: =>Unit) = ExecutionContext.global.execute(new Runnable {
		def run() = body
	})
}
