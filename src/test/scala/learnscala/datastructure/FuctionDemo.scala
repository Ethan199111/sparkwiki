package learnscala.datastructure

import scala.collection.mutable.ArrayBuffer

object FuctionDemo extends App {
	functionOps1
	functionOps2
	functionOps3
	functionOps4

	/**
		* 作为值传递的函数
		* 将一个函数作为值传递给另外一个函数变量的时候，约定需要在函数后面加上：空格和下划线
		* 相当于数据库中的别名，或者数据库表对应的视图
		*/
	def functionOps1: Unit = {
		def sayHi(name:String) = println("Hello, " + name)

		def sayHello = sayHi _

		sayHello("xpleaf")
	}
	/**
		* 匿名函数，说白了就是没有函数名字
		*     匿名函数就和java中的匿名内部类一样，是只适合使用一次的函数
		*     一般如果一个函数的参数是一个函数，这种情况下多用匿名函数来作为参数进行传递
		*/
	def functionOps2: Unit = {
		val printName = (name:String) => println("你好，" + name)
		printName("xpleaf")
	}

	/**
		* scala的高阶函数，就是函数的 [参数是函数] 的函数，把这种函数称为高阶函数
		*/
	def functionOps3: Unit = {

		// 1.匿名函数作为参数
		def highOrderFunc(name:String, func:(String) => Unit): Unit = {
			func(name)
		}

		highOrderFunc("xpleaf", (name:String) => println("Hello, " + name))

		// 2.将匿名函数作为返回值传递给另外一个函数
		def getGoodBayFunction(gMsg: String) = (gName: String) => println(gMsg + ", " + gName)

		val goodbayFunction = getGoodBayFunction("good bye")
		goodbayFunction("xpleaf")

	}

	/**
		* 对于匿名函数的省略写法
		*/
	def functionOps4: Unit = {
		val ab = ArrayBuffer[Int](1, 2, 3, 4, 5)

		// val newAB = ab.map((x:Int) => x * 100)
		// val newAB = ab.map((x) => x * 100)
		// val newAB = ab.map(x => x * 100)
		val newAB = ab.map(100 * _)
		println(newAB)  // ArrayBuffer(100, 200, 300, 400, 500)

	}
}