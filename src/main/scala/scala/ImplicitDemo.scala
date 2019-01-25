package scala

object ImplicitDemo {

	def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate

	implicit def double2Int(d: Double): Int = d.toInt

	def main(args: Array[String]): Unit = {
		implicit val currentTaxRate: Float = 0.08F
		//implicit val another: Float = 0.06F
		val tax = calcTax(50000F) // 4000.0
		println(tax)
		val i: Int = 3.5
		println(i)

	}
}
