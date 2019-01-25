package dl.activation

object Sigmoid {
	def apply(x : Double) : Double = 1d/(1d + math.exp(-x))
}
