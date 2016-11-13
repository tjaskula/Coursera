def countSegments(starts: List[Int], ends: List[Int], points: List[Int]): String = {

  def count(l: List[Int], point: Int, fn: (Int, Int) => Boolean, index: Int = 0): Int = l match {
    case Nil => index
    case x :: xs if fn(x, point) => count(xs, point, fn, index + 1)
    case _ => index
  }

  def countSegmentsIter(cnt: String, s: List[Int], e: List[Int], p: List[Int]): String = p match {
    case Nil => cnt
    case x :: xs => {
      val cntStarts = count(s, x, (x, point) => x <= point)
      val cntEnds = count(e, x, (x, point) => x < point)
      countSegmentsIter(cnt + (cntStarts - cntEnds) + " ", s, e, xs)
    }
  }

  countSegmentsIter("", starts.sorted, ends.sorted, points).trim()
}

val s1 = List(0, 7)
val e1 = List(5, 10)
val p1 = List(1, 6, 11)

countSegments(s1, e1, p1)

val s2 = List(0, -3, 7)
val e2 = List(5, 2, 10)
val p2 = List(1, 6)

countSegments(s2, e2, p2)

val s3 = List(-10)
val e3 = List(10)
val p3 = List(-100, 100, 0)

countSegments(s3, e3, p3)

val s4 = List(-4, -2, -4, -4)
val e4 = List(-2, 4, -2, 2)
val p4 = List(14, -2, 2, 30, 9)

countSegments(s4, e4, p4)

val s5 = List(4, 1)
val e5 = List(0, 3)
val p5 = List(2)

countSegments(s5, e5, p5)