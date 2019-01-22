package deeplearn.activation

object FastSigmoid {

	private final val MAX_SIGMOID = 8
	private final val SIGMOID_TABLE_SIZE = 1024
	private final val LOG_TABLE_SIZE = 1024
	private final val logTable = {
		Array.tabulate(LOG_TABLE_SIZE + 1)(i =>
			math.log((i + 1e-5) / LOG_TABLE_SIZE).toFloat
		)
	}
	private final val sigmoidTable = {
		Array.tabulate(SIGMOID_TABLE_SIZE + 1)(i => {
			val x = (i * 2 * MAX_SIGMOID).toDouble / SIGMOID_TABLE_SIZE - MAX_SIGMOID
			1.0f / (1.0f + math.exp(-x).toFloat)
		})
	}

	def sigmoid(x: Float): Float = {
		if (x < -MAX_SIGMOID)
			0.0f
		else if (x > MAX_SIGMOID)
			1.0f
		else
			sigmoidTable(((x + MAX_SIGMOID) * SIGMOID_TABLE_SIZE / MAX_SIGMOID / 2).toInt)
	}

	def log(x: Float): Float = {
		if (x > 1.0) 0.0f else logTable((x * LOG_TABLE_SIZE).toInt)
	}
}