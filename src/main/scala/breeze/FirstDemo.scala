package breeze

import breeze.linalg.SparseVector

object FirstDemo extends App {
	val x = SparseVector.zeros[Double](5)
	x(0)
}
