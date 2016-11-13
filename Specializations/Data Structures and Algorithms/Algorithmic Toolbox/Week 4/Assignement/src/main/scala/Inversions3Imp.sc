def inv(list : Array[Int]) : Long = doInv(list)._1

def doInv(list : Array[Int]) : (Long, Array[Int]) =
  if (list.length <= 1) {
    (0, list)
  } else {
    val (left, right) = list.splitAt(list.length / 2)
    val (leftCount, leftList) = doInv(left)
    val (rightCount, rightList) = doInv(right)
    val (mergeCount, mergeList) = doMerge(leftList, rightList)
    (leftCount + rightCount + mergeCount, mergeList)
  }

def doMerge(left : Array[Int], right : Array[Int], count : Long = 0) : (Long, Array[Int]) =
  (left, right) match {
    case (Array(), r) => (count, r)
    case (l, Array()) => (count, l)
    case _ =>
      val lhead = left.head
      val ltail = left.tail
      val rhead = right.head
      val rtail = right.tail
      if (lhead <= rhead) {
        val (lcount, list) = doMerge(ltail, right, count)
        (count + lcount, lhead +: list)
      } else {
        val (rcount, list) = doMerge(left, rtail, count)
        (count + left.length + rcount, rhead +: list)
      }
  }

val l1 = Array(2, 3, 9, 2, 9)
inv(l1)

val l2 = Array(2, 1, 1, 3, 4, 9, 5)
inv(l2)

val l3 = Array(9, 8, 7, 3, 2, 1)
inv(l3)

val l4 = Array(9, 9, 8, 8, 7, 7, 3, 3, 2, 2, 1, 1)
inv(l4)

val l5 = Array(8, 15, 3, 1)
inv(l5)

val l6 = Array(1, 5, 4, 8, 10, 2, 6, 9, 3, 7)
inv(l6)

val l7 = Array(1, 5, 4, 8, 10, 2, 6, 9, 12, 11, 3, 7)
inv(l7)