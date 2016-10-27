abstract class BinaryTree extends {
  override def toString: String = {
    def toStringIter(tree: BinaryTree): String =
    tree match {
      case Node(k, l, r) => "Node(" + k + ", " + toStringIter(l) + ", " + toStringIter(r) + ")"
      case Leaf(k) => "Leaf(" + k + ")"
    }
    toStringIter(this)
  }
}

case class Leaf(key: Int) extends BinaryTree
case class Node(key: Int, left: BinaryTree, right: BinaryTree) extends BinaryTree

def empty: Int = -1

def build(n: Int, read: Int => Int): Array[Int] = {
  val a = Array.ofDim[Int](n)
  for (i <- 0 until n) a(i) = read(i)
  a
}

def buildBinaryTree(keys: Array[Int], left: Array[Int], right: Array[Int]): BinaryTree = {
  def buildBinaryTreeFromIndexes(key: Int, left: Int, right: Int): BinaryTree =
    (key, left, right) match {
      case (k, l, r) => Leaf(k)
      case (k, -1, r) => Leaf(k)
      case (k, l, -1) => Leaf(k)
      case (k, -1, -1) => Leaf(k)
    }
  buildBinaryTreeFromIndexes(0, 0, 0)
}

def buildTree(n: Int, read: Int => Int): BinaryTree = {
  val keys = build(n, read)
  val left = build(n, read)
  val right = build(n, read)
  buildBinaryTree(keys, left, right)
}

val rawTree1 = Array[(Int, Int, Int)]((4, 1, 2), (2, 3, 4), (5, -1, -1), (1, -1, -1), (3, -1, -1))