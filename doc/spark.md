spark问题总结 https://zhuanlan.zhihu.com/p/49169166

# 对于 Spark 中的数据倾斜问题你有什么好的方案

1. 数据源倾斜
2. 调整并行度
3. 使用自定义 Partitioner，
4. 使用 Map 侧 Join 代替 Reduce 侧 Join（内存表合并）
5. 给倾斜 Key 加上随机前缀等

数据倾斜是如何造成的 在 Spark 中，同一个 Stage 的不同 Partition 可以并行处理，
而具有依赖关系的不同 Stage 之间是串行处理的。假设某个 Spark Job 分为 Stage 0和 Stage 1两个 Stage，
且 Stage 1依赖于 Stage 0，那 Stage 0完全处理结束之前不会处理Stage 1。
而 Stage 0可能包含 N 个 Task，这 N 个 Task 可以并行进行。如果其中 N-1个 Task 都在10秒内完成，
而另外一个 Task 却耗时1分钟，那该 Stage 的总时间至少为1分钟。换句话说，一个 Stage 所耗费的时间，
主要由最慢的那个 Task 决定。由于同一个 Stage 内的所有 Task 执行相同的计算，
在排除不同计算节点计算能力差异的前提下，不同 Task 之间耗时的差异主要由该 Task 所处理的数据量决定

https://zhuanlan.zhihu.com/p/49185277
https://zhuanlan.zhihu.com/p/51011021

# spark on yarn

https://marsishandsome.github.io/2018/03/Spark_on_Yarn%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90

