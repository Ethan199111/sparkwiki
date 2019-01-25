package scala

case class Test(name: String = "junxin", id: Int = 0)

object Demo extends App {
	val t = Test()
	println(t.name)
	println(t.id)
}