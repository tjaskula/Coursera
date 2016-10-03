import scala.annotation.tailrec

class TreeDepth(val parents: List[(Int, Int)], n: Int) {
  val depths = Array.fill[Int](n)(0)

  @tailrec
  private def getParentStack(idx: Int, parent: Int, stack: List[Int] = Nil): List[Int] = {
    if (parent != -1 && depths(idx) == 0) getParentStack(parent, parents(parent)._2, idx :: stack)
    else idx :: stack
  }

  @tailrec
  private def countDepth(stack: List[Int], maxDepth: Int): Int =
    stack match {
      case Nil => maxDepth
      case x::xs => {
        depths(x) = math.max(depths(x), maxDepth)
        countDepth(xs, depths(x) + 1)
      }
    }

  def maxDepth(): Int = {
    @tailrec
    def maxDepthIter(parents: List[(Int, Int)], maxDepth: Int): Int =
      parents match {
        case Nil => maxDepth
        case x::xs => {
          val indx = x._1
          val parent = x._2
          val parentStack = getParentStack(indx, parent)
          val newDepth = depths(indx)
          val depth = countDepth(parentStack, newDepth)
          maxDepthIter(xs, if (maxDepth < depth) depth else maxDepth)
        }
      }
    maxDepthIter(parents, 0)
  }
}


new TreeDepth(List((0, 4), (1, -1), (2, 4), (3, 1), (4, 1)), 5).maxDepth()
new TreeDepth(List((0, -1), (1, 0), (2, 4), (3, 0), (4, 3)), 5).maxDepth()
new TreeDepth(List((0, 9), (1, 7), (2, 5), (3, 5), (4, 2), (5, 9), (6, 9), (7, 9), (8, 2), (9, -1)), 10).maxDepth()
