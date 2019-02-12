package ml
import com.github.fommil.netlib.{F2jBLAS, BLAS => NetlibBLAS}

object BLAS extends Serializable {

	@transient private var _f2jBLAS: NetlibBLAS = _
	@transient private var _nativeBLAS: NetlibBLAS = _

	def f2jBLAS: NetlibBLAS = {
		if (_f2jBLAS == null) {
			_f2jBLAS = new F2jBLAS
		}
		_f2jBLAS
	}

	/**
		* dot(x, y)
		*/
	def dot(x: Array[Double], y: Array[Double]): Double = {
		val n = x.length
		f2jBLAS.ddot(n, x, 1, y, 1)
	}


	def main(args: Array[String]): Unit = {
		val x = Array(1d,2,3)
		val y = Array(1d,2,3)
		val z = dot(x, y)
		println(z)
	}
}