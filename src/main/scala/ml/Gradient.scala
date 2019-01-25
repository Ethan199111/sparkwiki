package ml

import breeze.linalg._

abstract class Gradient extends Serializable {
	def compute(data: Vector[Double], label: Double, weights: Vector[Double]) :
	(Vector[Double], Double) = {
		val gradient = new DenseVector[Double](weights.size)
		val loss = compute(data, label, weights, gradient)
		(gradient, loss)
	}

	def compute(data: Vector[Double], label: Double, weights: Vector[Double],
							cumGradient: Vector[Double]): Double

	def log1pExp(x: Double): Double = {
		if (x > 0) {
			x + math.log1p(math.exp(-x))
		} else {
			math.log1p(math.exp(x))
		}
	}
}

class LogisticGradient(numClasses: Int) extends Gradient {
	def this() = this(2)
	override def compute(data: Vector[Double], label: Double, weights: Vector[Double],
							cumGradient: Vector[Double]): Double = {
		val dataSize = data.size
		// (weights.size / dataSize + 1) is number of classes
		require(weights.size % dataSize == 0 && numClasses == weights.size / dataSize + 1)
		val margin = -1.0 * BLAS.dot(data.toArray, weights.toArray)
		val multiplier = (1.0/(1.0 + math.exp(margin))) - label
		axpy(multiplier, data, cumGradient)
		if (label > 0) {
			log1pExp(margin)
		} else {
			log1pExp(margin) - margin
		}
	}
}