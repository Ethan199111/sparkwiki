package learnscala.breeze

import breeze.linalg._
import breeze.stats.mean
import breeze.plot._

object demo extends App {

	val x = DenseVector.zeros[Double](5)
	println(x)

	val y = SparseVector.zeros[Double](5)
	println(y)

	x(1) = 2
	println(x)
	x(3 to 4) := .5
	println(x)

	x(0 to 1) := DenseVector(.1,.2)
	println(x)

	val m = DenseMatrix.zeros[Int](5,5)
	println(m)

	println((m.rows, m.cols))

	m(4,::) := DenseVector(1,2,3,4,5).t

	println(m)

	val dm = DenseMatrix((1.0,2.0,3.0),
		(4.0,5.0,6.0))

	val res = dm(::, *) + DenseVector(3.0, 4.0)

	println(res)

	res(::, *) := DenseVector(3.0, 4.0)
	println(res)

	val me =  mean(dm(*, ::))
	println(me)

	val f = Figure()
	val p = f.subplot(0)
	val x1 = linspace(0.0,1.0)
	p += plot(x1, x1 :^ 2.0)
	p += plot(x1, x1 :^ 3.0, '.')
	p.xlabel = "x axis"
	p.ylabel = "y axis"
	f.saveas("lines.png")

}
