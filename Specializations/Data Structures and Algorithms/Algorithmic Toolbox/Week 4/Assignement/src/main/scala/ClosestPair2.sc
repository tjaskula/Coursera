import scala.math.{BigDecimal, _}

class Point(var x: Int, var y: Int) {
  override def toString: String = "Point(" + x + ", " + y + ")"
}

def getDistance(p1: Point, p2: Point): Double =
  sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2))

def closestPairsBrutForce(subPoints: List[Point]): Double = {
  var minDistance = Double.PositiveInfinity
  for (i <- subPoints.indices; j <- i + 1 until subPoints.length) {
    val distance = getDistance(subPoints(i), subPoints(j))
    if (distance < minDistance)
      minDistance = distance
  }
  minDistance
}

def stripClosest(strip: List[Point], minDistance: Double) = {
  var min = minDistance

  for (i <- strip.indices; j <- i + 1 until strip.length if (strip(j).y - strip(i).y) < min) {
    val distance = getDistance(strip(i), strip(j))
    if (distance < min)
      min = getDistance(strip(i), strip(j))
  }
  min
}

def closestPairs(points: List[Point]): Double = {
  def closestPairsIter(xP: List[Point], yP: List[Point]): Double = {
    if (xP.length <= 3)
      closestPairsBrutForce(xP)
    else {
      val mid = xP.length / 2
      val xm = xP(mid)
      val (xL, xR) = xP splitAt mid
      val (yL, yR) = yP partition (p => p.x <= xm.x)
      val dL = closestPairsIter(xL, yL)
      val dR = closestPairsIter(xR, yR)
      val dmin = min(dL, dR)

      val yS = yP filter (p => abs(xm.x - p.x) < dmin)

      var closest = dmin
      for (i <- yS.indices) {
        var k = i + 1
        while (k < yS.length && (yS(k).y - yS(i).y) < dmin) {
          val distance = getDistance(yS(k), yS(i))
          if (distance < closest)
            closest = distance
          k = k + 1
        }
      }

      closest
    }
  }
  val res = closestPairsIter(points.sortWith((t1, t2) => t1.x < t2.x), points.sortWith((t1, t2) => t1.y < t2.y))
  BigDecimal(res).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
}

val p1 = List(new Point(0, 0), new Point(3, 4))
closestPairs(p1)

val p2 = List(new Point(7, 7), new Point(1, 100), new Point(4, 8), new Point(7, 7))
closestPairs(p2)

val p3 = List(new Point(4, 4), new Point(-2, -2), new Point(-3, -4), new Point(-1, 3),
  new Point(2, 3), new Point(-4, 0), new Point(1, 1), new Point(-1, -1), new Point(3, -1),
  new Point(-4, 2), new Point(-2, 4))
closestPairs(p3)

val p4 = List(new Point(2, 3), new Point(12, 30), new Point(40, 50), new Point(5, 1),
  new Point(12, 10), new Point(3, 4))
closestPairs(p4)