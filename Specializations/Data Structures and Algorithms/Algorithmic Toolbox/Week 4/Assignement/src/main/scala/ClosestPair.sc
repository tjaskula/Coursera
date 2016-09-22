import math._

class Point(var x: Int, var y: Int) {
  override def toString: String = "Point(" + x + ", " + y + ")"

}

def getDistance(p1: Point, p2: Point): Double =
  sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2))

def closestPairsBrutForce(subPoints: List[Point]): Double = {
  var minDistance = Double.PositiveInfinity
  for (i <- 0 until subPoints.length; j <- i + 1 until subPoints.length) {
    val distance = getDistance(subPoints(i), subPoints(j))
    if (distance < minDistance)
      minDistance = distance
  }
  minDistance
}

def stripClosest(strip: List[Point], minDistance: Double) = {
  var min = minDistance

  for (i <- 0 until strip.length; j <- i + 1 until strip.length) {
    if ((strip(j).y - strip(i).y) < minDistance) {
      if (getDistance(strip(i), strip(j)) < minDistance)
        min = getDistance(strip(i), strip(j))
    }
  }
  min
}

def closestPairs(points: List[Point]): Double = {
  def closestPairsIter(subPointsX: List[Point], subPointsY: List[Point]): Double = {
    if (subPointsX.length <= 3)
      closestPairsBrutForce(subPointsX)
    else {
      val mid = subPointsX.length / 2
      val midPoint = subPointsX(mid)
      val (subPointsYL, subPointsYR) = subPointsY splitAt mid
      val (subPointsXL, subPointsXR) = subPointsX splitAt mid
      val sigmaL = closestPairsIter(subPointsXL, subPointsXR)
      val sigmaR = closestPairsIter(subPointsYL, subPointsYR)
      val sigma = min(sigmaL, sigmaR)
      val candidates = subPointsY filter (p => abs(p.x - midPoint.x) < sigma)
      min(sigma, stripClosest(candidates, sigma))
    }
  }
  closestPairsIter(points.sortWith((t1, t2) => t1.x < t2.x), points.sortWith((t1, t2) => t1.y < t2.y))
}

val p1 = List(new Point(0, 0), new Point(3, 4))
closestPairsBrutForce(p1)

val p2 = List(new Point(7, 7), new Point(1, 100), new Point(4, 8), new Point(7, 7))
closestPairsBrutForce(p2)

val p3 = List(new Point(4, 4), new Point(-2, -2), new Point(-3, -4), new Point(-1, 3),
  new Point(2, 3), new Point(-4, 0), new Point(1, 1), new Point(-1, -1), new Point(3, -1),
  new Point(-4, 2), new Point(-2, 4))
closestPairsBrutForce(p3)