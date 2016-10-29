import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.util.StringTokenizer

object TreeOrders {

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

  def main(args: Array[String]): Unit = {
    new Thread(null, new Runnable() {
      def run() {
        try
          runTree()

        catch {
          case e: IOException => {
          }
        }
      }
    }, "1", 1 << 26).start()
  }

  @throws[IOException]
  def runTree() {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    val t = buildTree(n, _ => scanner.nextInt)
    println(inOrderTraversal(t, new StringBuilder()).toString().trim)
    println(preOrderTraversal(t, new StringBuilder()).toString().trim)
    println(postOrderTraversal(t, new StringBuilder()).toString().trim)
  }

  class FastScanner(val stream: InputStream) {

    var br: BufferedReader = null
    var st: StringTokenizer = null

    try
      br = new BufferedReader(new InputStreamReader(stream))

    catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }

    def next: String = {
      while (st == null || !st.hasMoreTokens)
        try
          st = new StringTokenizer(br.readLine)

        catch {
          case e: IOException => {
            e.printStackTrace()
          }
        }
      st.nextToken
    }

    def nextInt: Int = next.toInt
    def nextLong: Long = next.toLong
  }
}