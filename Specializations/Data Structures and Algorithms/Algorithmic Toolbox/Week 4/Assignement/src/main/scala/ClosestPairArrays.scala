import java.io._
import java.util.{StringTokenizer}
import math._

object ClosestPairArrays {

  class Point(var x: Int, var y: Int) {
    override def toString: String = "Point(" + x + ", " + y + ")"

  }

  def getDistance(p1: Point, p2: Point): Double =
    sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2))

  def closestPairsBrutForce(subPoints: Array[Point], size: Int): Double = {
    var minDistance = Double.PositiveInfinity
    for (i <- 0 until size; j <- i + 1 until size) {
      val distance = getDistance(subPoints(i), subPoints(j))
      if (distance < minDistance)
        minDistance = distance
    }
    minDistance
  }

  def stripClosest(strip: Array[Point], size: Int, minDistance: Double) = {
    var min = minDistance

    for (i <- 0 until size; j <- i + 1 until size) {
      if ((strip(j).y - strip(i).y) < min) {
        val distance = getDistance(strip(i), strip(j))
        if (distance < min)
          min = distance
      }
    }
    min
  }

  def closestPairs(points: Array[Point], s: Int): Double = {
    def closestPairsIter(subPointsX: Array[Point], subPointsY: Array[Point], size: Int): Double = {
      if (size <= 100)
        closestPairsBrutForce(subPointsX, size)
      else {
        val mid = size / 2
        val midPoint = subPointsX(mid)
        val subPointsYL = subPointsY.slice(0, mid)
        val subPointsYR = subPointsY.slice(mid, size)
        val subPointsXL = subPointsX.slice(0, mid)
        val subPointsXR = subPointsX.slice(mid, size)
        val sigmaL = closestPairsIter(subPointsXL, subPointsYL, mid)
        val sigmaR = closestPairsIter(subPointsXR, subPointsYR, size - mid)
        val sigma = min(sigmaL, sigmaR)
        val candidates = Array.ofDim[Point](size)
        var j = 0
        for (i <- 0 until size) {
          if (abs(subPointsX(i).x - midPoint.x) < sigma) {
            candidates(j) = subPointsX(i)
            j = j + 1
          }
        }

        min(sigma, stripClosest(candidates, j, sigma))
      }
    }
    val res = closestPairsIter(points.sortWith((t1, t2) => t1.x < t2.x), points, s)
    BigDecimal(res).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    /*def buildStartsEnds(n: Int, l: List[Point]): List[Point] =
      if (n == 0) l
      else buildStartsEnds(n - 1, new Point(scanner.nextInt, scanner.nextInt) :: l)

    val points = buildStartsEnds(n, Nil)*/

    val points = Array.ofDim[Point](n)
    for (i <- 0 until n)
      points(i) = new Point(scanner.nextInt, scanner.nextInt)

    println(closestPairs(points, n))
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