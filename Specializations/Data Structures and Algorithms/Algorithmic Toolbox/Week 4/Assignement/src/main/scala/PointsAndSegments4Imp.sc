def countSegmentsFold(starts: Array[Int], ends: Array[Int], points: Array[Int]): String = {
  val s = starts.sorted
  val e = ends.sorted

  def getDeltas(point: Int, cnt: List[Int]): List[Int] = {
    val index1 = s.view.takeWhile(_ <= point).force.length
    val index2 = e.view.takeWhile(_ < point).force.length
    val delta = index1 - index2
    delta :: cnt
  }
  points.foldRight(Nil: List[Int])(getDeltas).mkString(" ")
}

def countSegmentsFold2(starts: Array[Int], ends: Array[Int], points: Array[Int]): String = {
  val s = starts.sorted
  val e = ends.sorted

  def getDeltas(point: Int, cnt: List[Int]): List[Int] = {
    var index1 = 0
    while (index1 < s.length && s(index1) <= point) {
      index1 += 1
    }

    var index2 = 0
    while (index2 < e.length && e(index2) < point) {
      index2 += 1
    }
    val delta = index1 - index2
    delta :: cnt
  }
  points.foldRight(Nil: List[Int])(getDeltas).mkString(" ")
}

val s1 = Array(0, 7)
val e1 = Array(5, 10)
val p1 = Array(1, 6, 11)

countSegmentsFold(s1, e1, p1)

val s2 = Array(0, -3, 7)
val e2 = Array(5, 2, 10)
val p2 = Array(1, 6)

countSegmentsFold(s2, e2, p2)

val s3 = Array(-10)
val e3 = Array(10)
val p3 = Array(-100, 100, 0)

countSegmentsFold(s3, e3, p3)

val s4 = Array(-4, -2, -4, -4)
val e4 = Array(-2, 4, -2, 2)
val p4 = Array(14, -2, 2, 30, 9)

countSegmentsFold(s4, e4, p4)

val s5 = Array(4, 1)
val e5 = Array(0, 3)
val p5 = Array(2)

countSegmentsFold(s5, e5, p5)