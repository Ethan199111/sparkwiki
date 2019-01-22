package scalalearn

case class TreeNode(var value: Int,
										var left: TreeNode = null,
										var right: TreeNode = null)

object Tree {
	def isSameTree(l: TreeNode, r : TreeNode) : Boolean = {
		if (l == null && r == null) {
			true
		} else if (l == null && r != null) {
			false
		} else if (l != null && r == null) {
			false
		} else if (l.value == r.value) {
			isSameTree(l.left, r.left) && isSameTree(l.right, r.right)
		} else {
			false
		}
	}
}

object TreeDemo extends App {
	val root = TreeNode(1)
	println(root.value)
	println(root.left)

	val left = TreeNode(2)
	root.left = left
	println(root.left.value)


	val root2 = TreeNode(1)
	root2.left = left
	println(Tree.isSameTree(root, root2))
}