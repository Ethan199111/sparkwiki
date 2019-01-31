package breeze

import breeze.linalg.SparseVector

object FirstDemo extends App {
	val x = SparseVector.zeros[Double](5)
	val y = SparseVector.zeros[Double](5)
	x(0) = 1
	y(0) = 2
	val z = x.dot(y)
	println(z)
	println(z.toString)
}
