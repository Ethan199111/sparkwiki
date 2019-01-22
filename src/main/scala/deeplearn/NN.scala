package deeplearn

import breeze.linalg._
import breeze.numerics._

object NN extends App {
	//Forward propogation
	val x1 = DenseVector(1.0, 0.0, 1.0)
	val y1 = DenseVector(1.0, 1.0, 1.0)

	val theta1 = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 0.0), (1.0, 0.0, 0.0))
	val theta2 = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 0.0), (1.0, 0.0, 0.0))
	val theta3 = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 0.0), (1.0, 0.0, 0.0))

	val a1 = x1

	val z2 = theta1 * a1
	val a2 = z2.map { x => 1 + sigmoid(x) }

	val z3 = theta2 * a2
	val a3 = z3.map { x => 1 + sigmoid(x) }

	val z4 = theta3 * a3
	val a4 = z4.map { x => 1 + sigmoid(x) }

	//Back propagation
	val errorLayer4 = a4 - DenseVector(1.0, 1.0, 1.0)
	val errorLayer3 = (theta3.t * errorLayer4) :* (a3 :* (DenseVector(1.0, 1.0, 1.0) - a3))
	val errorLayer2 = (theta2.t * errorLayer3) :* (a2 :* (DenseVector(1.0, 1.0, 1.0) - a2))

	//Compute delta values
	val delta1 = errorLayer2 * a2.t
	val delta2 = errorLayer3 * a3.t
	val delta3 = errorLayer4 * a4.t


	//Gradient descent
	val m = 1
	val alpha = .0001
	val x = DenseVector(1.0, 0.0, 1.0)
	val y = DenseVector(1.0, 1.0, 1.0)

	val pz1 = delta1 - (alpha / m) * (x.t * (delta1 * x - y))
	val p1z1 = sigmoid(delta1 * x) + 1.0
	println(p1z1)

	val pz2 = delta2 - (alpha / m) * (x.t * (delta2 * x - y))
	val p1z2 = sigmoid(delta2 * p1z1) + 1.0
	println(p1z2)

	val pz3 = delta3 - (alpha / m) * (x.t * (delta3 * x - y))
	val p1z3 = sigmoid(delta3 * p1z2) + 1.0
	println(p1z3)
}
