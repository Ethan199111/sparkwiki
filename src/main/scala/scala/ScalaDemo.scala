package scala

object ScalaDemo extends App {
	val i = "hello world"
	println(i)
	var count = 0 ;
	for ( i <- (0 until 100).par)
		synchronized(count += 1)
	println(count)
}
