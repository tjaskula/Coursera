object SetRangeSum {

  abstract class SplayTree
  case class Node(key: Int, sum: Long, left: SplayTree, right: SplayTree, parent: SplayTree) extends SplayTree
  case class Empty() extends SplayTree

  def getSum(t: SplayTree): Long = t match {
    case Empty() => 0
    case Node(_, s, _, _, _) => s
  }

  def calculateNodeSum(t: SplayTree): SplayTree = t match {
    case Empty() => t
    case Node(k, s, l, r, p) =>
      val sumLeft = getSum(l)
      val sumRight = getSum(r)
      Node(k, k + sumLeft + sumRight, l, r, p)
  }
}