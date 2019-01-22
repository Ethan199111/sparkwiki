package learnscala.datastructure

object ListDemo extends App {
	val list = List(1,2,3,4,5)
	println(list)
	println(list.head)
	println(list.tail)

	println(recursion(list))


	def recursion(list:List[Int]):Int = {
		if(list.isEmpty) {
			return 0  // 使用return显式结束程序的运行，否则0只是该if语句的返回值，并不会结束程序的运行，当然如果用else不用return也行
		}
		list.head + recursion(list.tail)
	}

	val newList = list.drop(1)
	println(newList)
}

//增
/*  A.++(B)  --> 在列表A的尾部对添加另外一个列表B,组成一个新的列表
 *  A.++:(B) --> 在列表A的首部对添加另外一个列表B,组成一个新的列表
 *  A.:::(B) --> 在列表A的首部对添加另外一个列表B,组成一个新的列表
 *  ------
 *  A.:+ (element) -->在列表A的尾部添加一个element，组成一个新的集合
 *  A.+: (element) -->在列表A的首部添加一个element，组成一个新的集合
 *  A.:: (element) -->在列表A的首部添加一个element，组成一个新的集合
 */


