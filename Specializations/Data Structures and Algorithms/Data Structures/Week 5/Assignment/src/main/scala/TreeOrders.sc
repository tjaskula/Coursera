abstract class BinaryTree extends {
  override def toString: String = {
    def toStringIter(tree: BinaryTree): String =
      tree match {
        case Empty() => "- "
        case Node(k, l, r) => "Node(" + k + ", " + toStringIter(l) + ", " + toStringIter(r) + ")"
        case Leaf(k) => "Leaf(" + k + ")"
      }
    toStringIter(this)
  }
}

def inOrderTraversal(tree: BinaryTree, sb: StringBuilder): StringBuilder = tree match {
  case Empty() => sb
  case Node(k, l, r) => {
    inOrderTraversal(l, sb)
    sb.append(k + " ")
    inOrderTraversal(r, sb)
  }
  case Leaf(k) => sb.append(k + " ")
}

def preOrderTraversal(tree: BinaryTree, sb: StringBuilder): StringBuilder = tree match {
  case Empty() => sb
  case Node(k, l, r) => {
    sb.append(k + " ")
    preOrderTraversal(l, sb)
    preOrderTraversal(r, sb)
  }
  case Leaf(k) => sb.append(k + " ")
}

def postOrderTraversal(tree: BinaryTree, sb: StringBuilder): StringBuilder = tree match {
  case Empty() => sb
  case Node(k, l, r) => {
    postOrderTraversal(l, sb)
    postOrderTraversal(r, sb)
    sb.append(k + " ")
  }
  case Leaf(k) => sb.append(k + " ")
}

case class Empty() extends BinaryTree
case class Leaf(key: Int) extends BinaryTree
case class Node(key: Int, left: BinaryTree, right: BinaryTree) extends BinaryTree

def buildBinaryTree(keys: Array[Int], lefts: Array[Int], rights: Array[Int]): BinaryTree = {
  def buildBinaryTreeFromIndexes(key: Int, left: Int, right: Int): BinaryTree =
    (key, left, right) match {
      case (k, l, r) if l > -1 && r > -1 =>
        Node(k, buildBinaryTreeFromIndexes(keys(l), lefts(l), rights(l)),
          buildBinaryTreeFromIndexes(keys(r), lefts(r), rights(r)))
      case (k, -1, r) if r > -1 =>
        Node(k, Empty(), buildBinaryTreeFromIndexes(keys(r), lefts(r), rights(r)))
      case (k, l, -1) if l > -1 =>
        Node(k, buildBinaryTreeFromIndexes(keys(l), lefts(l), rights(l)), Empty())
      case (k, -1, -1) => Leaf(k)
    }

  def findStartIndex(index: Int, left: Int, right: Int): Int = {
    if (left == -1 && right == -1) findStartIndex(index + 1, lefts(index + 1), rights(index + 1))
    else index
  }

  val startIndex = findStartIndex(0, lefts(0), rights(0))
  buildBinaryTreeFromIndexes(keys(startIndex), lefts(startIndex), rights(startIndex))
}

def buildTree(n: Int, read: Unit => Int): BinaryTree = {
  val keys = Array.ofDim[Int](n)
  val left = Array.ofDim[Int](n)
  val right = Array.ofDim[Int](n)
  for (i <- 0 until n) {
    keys(i) = read()
    left(i) = read()
    right(i) = read()
  }

  buildBinaryTree(keys, left, right)
}

var i = 0
def readLine(a: Array[Int]) = {
  val v = a(i)
  i = i + 1
  v
}

val a1 = Array[Int](4, 1, 2, 2, 3, 4, 5, -1, -1, 1, -1, -1, 3, -1, -1)

val t1 = buildTree(5, i => readLine(a1))
inOrderTraversal(t1, new StringBuilder()).toString().trim
preOrderTraversal(t1, new StringBuilder()).toString().trim
postOrderTraversal(t1, new StringBuilder()).toString().trim

val a2 = Array[Int](0, 7, 2, 10, -1, -1, 20, -1, 6, 30, 8, 9, 40, 3, -1, 50, -1, -1, 60, 1, -1, 70, 5, 4, 80, -1, -1, 90, -1, -1)

i = 0
val t2 = buildTree(10, i => readLine(a2))
inOrderTraversal(t2, new StringBuilder()).toString().trim
preOrderTraversal(t2, new StringBuilder()).toString().trim
postOrderTraversal(t2, new StringBuilder()).toString().trim