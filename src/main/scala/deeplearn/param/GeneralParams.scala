package deeplearn.param

import org.apache.spark.ml.param.{DoubleParam, IntParam, LongParam, Params}

trait GeneralParams extends Params {
	val validateFaction = new DoubleParam(this, "validateFaction", "validateFaction")
	val lambda1 = new DoubleParam(this, "lambda1", "lambda1")
	val lambda2 = new DoubleParam(this, "lambda2", "lambda2")
	val alpha = new DoubleParam(this, "alpha", "alpha")
	val beta = new DoubleParam(this, "beta", "beta")
	val batchSize = new IntParam(this, "batchSize", "batchSize")
	val dim = new LongParam(this, "dim", "dim")

	def getValidateFaction: Double = $(validateFaction)
	def getLambda1: Double = $(lambda1)
	def getLambda2: Double = $(lambda2)
	def getAlpha: Double = $(alpha)
	def getBeta: Double = $(beta)
	def getBatchSize: Int = $(batchSize)
	def getDim: Long = $(dim)

	def setValidateFaction(value: Double): this.type = set(validateFaction, value)
	def setLambda1(value: Double): this.type = set(lambda1, value)
	def setLambda2(value: Double): this.type = set(lambda2, value)
	def setAlpha(value: Double): this.type = set(alpha, value)
	def setBeta(value: Double): this.type = set(beta, value)
	def setBatchSize(value: Int): this.type = set(batchSize, value)
	def setDim(value: Long): this.type = set(dim, value)

	setDefault(
		validateFaction -> 0.3,
		lambda1 -> 15,
		lambda2 -> 15,
		alpha -> 0.1,
		beta -> 1.0,
		batchSize -> 10000,
		dim -> -1
	)
}