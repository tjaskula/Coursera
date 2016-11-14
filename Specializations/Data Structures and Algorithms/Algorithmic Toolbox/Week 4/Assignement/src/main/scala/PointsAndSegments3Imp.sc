def countSegments(starts: Array[Int], ends: Array[Int], points: Array[Int]): String = {
  val cnt = Array.ofDim[Int](points.length)

  val s = starts.sorted
  val e = ends.sorted

  for (i <- points.indices) {
    var index1 = 0
    s.indices.iterator.takeWhile(_ => s(index1) <= points(i)).foreach(_ => index1 += 1)

    var index2 = 0
    e.indices.iterator.takeWhile(_ => e(index2) < points(i)).foreach(_ => index2 += 1)


    cnt(i) = index1 - index2
  }

  cnt.mkString(" ")
}

val s1 = Array(0, 7)
val e1 = Array(5, 10)
val p1 = Array(1, 6, 11)

countSegments(s1, e1, p1)

val s2 = Array(0, -3, 7)
val e2 = Array(5, 2, 10)
val p2 = Array(1, 6)

countSegments(s2, e2, p2)

val s3 = Array(-10)
val e3 = Array(10)
val p3 = Array(-100, 100, 0)

countSegments(s3, e3, p3)

val s4 = Array(-4, -2, -4, -4)
val e4 = Array(-2, 4, -2, 2)
val p4 = Array(14, -2, 2, 30, 9)

countSegments(s4, e4, p4)

val s5 = Array(4, 1)
val e5 = Array(0, 3)
val p5 = Array(2)

countSegments(s5, e5, p5)