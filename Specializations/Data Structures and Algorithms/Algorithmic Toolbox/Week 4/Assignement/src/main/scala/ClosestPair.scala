import java.io._
import java.util.{StringTokenizer}
import math._

object ClosestPair {

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

    for (i <- 0 until strip.length; j <- i + 1 until strip.length if (strip(j).y - strip(i).y) < min) {
      val distance = getDistance(strip(i), strip(j))
      if (distance < min)
        min = getDistance(strip(i), strip(j))
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
        val sigmaL = closestPairsIter(subPointsXL, subPointsYL)
        val sigmaR = closestPairsIter(subPointsXR, subPointsYR)
        val sigma = min(sigmaL, sigmaR)
        val candidates = subPointsX filter (p => abs(p.x - midPoint.x) < sigma)
        min(sigma, stripClosest(candidates, sigma))
      }
    }
    val res = closestPairsIter(points.sortWith((t1, t2) => t1.x < t2.x), points.sortWith((t1, t2) => t1.y < t2.y))
    BigDecimal(res).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    def buildStartsEnds(n: Int, l: List[Point]): List[Point] =
      if (n == 0) l
      else buildStartsEnds(n - 1, new Point(scanner.nextInt, scanner.nextInt) :: l)

    val points = buildStartsEnds(n, Nil)

    println(closestPairs(points))
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
  }
}