package ml

import breeze.linalg._

abstract class Updater extends Serializable {
	def compute(weightsOld: Vector[Double],
							 gradient: Vector[Double],
							 stepSize: Double,
							 iter: Int,
							 regParam: Double): (Vector[Double], Double)
}

// no l1 or l2
class SimpleUpdater extends Updater {
	override def compute(weightsOld: Vector[Double],
							gradient: Vector[Double],
							stepSize: Double,
							iter: Int,
							regParam: Double): (Vector[Double], Double) = {
		// learning rate decay
		val thisIterStepSize = stepSize / math.sqrt(iter)
		val weights = weightsOld
		axpy(-thisIterStepSize, gradient.toDenseVector, weights.toDenseVector)
		(weights, 0)
	}
}

// l1


// l2



