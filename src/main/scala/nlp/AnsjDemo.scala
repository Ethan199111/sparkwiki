package nlp

import org.ansj.splitWord.analysis.ToAnalysis

object AnsjDemo extends  App {

	//val str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题" +
		//"都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!"
	//val res = ToAnalysis.parse(str)
	//println(res)

	val str2 = "我操你大爷"
	val str1 = "我日你大爷"
	println(ToAnalysis.parse(str2))
	println(ToAnalysis.parse(str1))

}
