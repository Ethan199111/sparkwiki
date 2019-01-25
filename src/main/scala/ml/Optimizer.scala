package ml

import breeze.linalg._

trait Optimizer extends Serializable {
	def optimize(data: Array[(Double, Vector[Double])], initialWeights: Vector[Double]) : Vector[Double]
}
