package learn

/**
	* map的操作;
	*/
object MapDemo {
	def main(args: Array[String]): Unit = {
		var map = Map[String, String]("name" -> "jason", "age" -> "500", "test_100" -> "test_100", "test_101" -> "test_101") //引用可变,支持读写操作;
		map += ("city" -> "北京") //新增
		println(map) //打印结果为:Map(city -> 北京, name -> jason, test_100 -> test_100, test_101 -> test_101, age -> 500);
		val map2 = Map[String, String]("sex" -> "男", "brand" -> "apple") //引用不可变,只能第一次写入值,之后只能读取;
		//map2 += ("test" -> "报错吗")  //此时不能加,直接报错;
		val map3 = scala.collection.mutable.Map[String, String]() //引用不可变,支持读写操作;
		map3 += ("test" -> "能添加吗") //添加单个元素;
		map3 += ("success" -> "添加成功了吗", "anthor" -> "另外一个") //添加多个元素;
		println(map3) //打印结果为:Map(success -> 添加成功了吗, anthor -> 另外一个, test -> 能添加吗);
		println(map3.keys) //返回所有的key;
		println(map3.values) //返回所有的value;
		println(map3.isEmpty) //当map为空时,返回true;
		var combine_map = map ++ map2 //合并两个map;
		println(combine_map) //打印结果为:Map(city -> 北京, name -> jason, test_100 -> test_100, test_101 -> test_101, age -> 500, brand -> apple, sex -> 男);
		combine_map -= ("city", "name") //删除指定的key;
		println(combine_map) //打印结果为:Map(test_100 -> test_100, test_101 -> test_101, age -> 500, brand -> apple, sex -> 男)
		println(combine_map.get("age").get) //返回指定key的值;
		println(combine_map.init) //返回所有元素，除了最后一个;
		println(combine_map.last) //返回最后一个元素;
		println(combine_map.max) //查找最大元素;
		println(combine_map.min) //查找最小元素;
		println(combine_map.mkString("")) //集合所有元素作为字符串显示;
		//println(combine_map.product) //返回集合中数字元素的积;注意得是num型;
		println(combine_map.size) //返回map的大小;
		println(combine_map.toArray) //集合转数组;
		println(combine_map.toBuffer) //返回缓冲区,包含了Map的所有元素;
		println(combine_map.toList) //返回List,包含了Map的所有元素;
		combine_map.getOrElse("test_101", "不存在") //根据key取value值,如果不存在返回后面的值;
		val keySet = combine_map.keys
		val key_iter = keySet.iterator //遍历,迭代map;
		while (key_iter.hasNext) {
			val key = key_iter.next
			println(key + ":" + combine_map.get(key).get)
		}
		println(combine_map.apply("brand")) //返回指定键的值，如果不存在返回 Map 的默认方法;
		println(combine_map.contains("test_key")) //如果map中存在指定 key,返回 true，否则返回 false;
		val age_count = combine_map.count(x => { //计算满足指定条件的集合元素数量;
			x._1.equals("age")
		})
		println(age_count) //打印结果为1;
		val drop_map = combine_map.drop(1) //返回丢弃最后n个元素新集合;
		println(drop_map) //打印结果为:Map(brand -> apple, sex -> 男);
		println(combine_map.empty) //返回相同类型的空map;
		println(map.equals(map2)) //如果两个 Map 相等(key/value 均相等)，返回true，否则返回false;
		println(combine_map.exists(x => { //判断集合中指定条件的元素是否存在;
			x._2.equals("男")
		}))
		println(combine_map.filter(x => { //返回满足指定条件的所有集合; 结果为:Map(brand -> apple);
			x._1.length > 4
		}))
		println(combine_map.filterKeys(x => { //返回符合指定条件的不可变 Map;
			x.equals("test_100")
		}))
		println(combine_map.find(x => { //查找集合中满足指定条件的第一个元素;
			x._1.equals(x._2)
		}))
		combine_map.foreach(x => { //循环map里的所有元素;
			println(x + "------------")
		})

	}
}