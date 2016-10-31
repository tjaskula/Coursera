object SplayTree {
  abstract class Tree()
  case class Node(key: Int, sum: Long, left: Tree, right: Tree, parent: Tree) extends Tree
  case class Empty() extends Tree

  def calculateNodeSum(t: Tree): Tree = t match {
    case Empty() => t
    case Node(k, s, l, r, p) =>
      val sumLeft = l match {case Empty() => 0; case Node(_, sl, _, _, _) => sl}
      val sumRight = r match {case Empty() => 0; case Node(_, sr, _, _, _) => sr}
      Node(k, k + sumLeft + sumRight, l, r, p)
  }

//  def update(t: Tree): Tree = {
//
//  }

//  def splay(t: Tree): Tree = {
//    def walkPerents(p: Tree): Tree = p match {
//      case Empty() => p
//      case Node(_, _, _, _, parent) => parent match {
//        case Empty() =>
//      }
//    }
//    t match {
//      case Empty() => t
//      case _ => walkPerents(t)
//    }
//  }
}