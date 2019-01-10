package learn

object ConcurrentDemo extends App {

	class MyThread extends Thread {
		override def run()  = {
			println("new thread running.")
		}
	}
	val t = new MyThread
	t.start()
	t.join()
	println("new thread joined")


}
