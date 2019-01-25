package spark.graphx

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import spark.SparkUtil

object SimpleGraphDemo {

	def main(args: Array[String]): Unit = {
		val spark = SparkUtil.startSpark()
		val sc = spark.sparkContext
		// Create an RDD for the vertices
		val users: RDD[(VertexId, (String, String))] =
			sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
				(5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))
		// Create an RDD for edges
		val relationships: RDD[Edge[String]] =
			sc.parallelize(Array(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
				Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))
		// Define a default user in case there are relationship with missing user
		val defaultUser = ("John Doe", "Missing")
		// Build the initial Graph
		val graph = Graph(users, relationships, defaultUser)
	}
}