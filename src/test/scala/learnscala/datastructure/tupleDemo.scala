package learnscala.datastructure

object tupleDemo extends App {
	val t = (1,3.14, "John", "Garry")
	println(t._1 +"\t " + t._2 +"\t " + t._3)
	val (first,second,third,fourth) = t // 这种赋值方式与Python是一样的，通过元组赋值给多个值
	println(first + "\t" + second + "\t" + third + "\t" + fourth)
	println("New York".partition ( _.isUpper))  // (NY,ew ork)

	t.productIterator.foreach(x => print(x +" "))

	val red = TrafficLight.RED  // red的类型为：TrafficLight.Value
	println(red)  // RED
	val red1 = TrafficLight.RED1  // 就是本object的引用，如这里，red1的"类型"为：TrafficLight.type，即对TrafficLight的引用
	println(red1) // TrafficLight

}

object TrafficLight extends Enumeration {
	//  val RED = TrafficLight;
	//  val YELLOW = TrafficLight;
	//  val GREEN = TrafficLight;
	val RED, YELLOW, GREEN = Value
	val RED1 = TrafficLight

}
