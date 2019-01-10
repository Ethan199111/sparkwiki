package top.yifan.myml.classification

/*
trait DemoParams extends Params {
	// parameters
	val thresholdValue = new IntParam(this, "thresholdValue", "thresholdValue")
	// getter
	def getThresholdValue: Int = $(thresholdValue)
	//setter
	def setThresholdValue(value: Int): this.type = set(thresholdValue, value)
	setDefault(
		thresholdValue -> 1
	)
}

class DemoClassifier (override val uid: String)
	extends ProbabilisticClassifier[Vector ,DemoClassifier , DemoModel] with DemoParams {
	def this() = this(Identifiable.randomUID(getClass.getName))
	override def copy(extra: ParamMap): DemoClassifier = null
	override protected def train(data: Dataset[_]):DemoModel = {
		null
	}
}

class DemoModel {


}
*/