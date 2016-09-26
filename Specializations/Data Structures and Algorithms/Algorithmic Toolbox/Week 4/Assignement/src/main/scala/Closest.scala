import java.io._
import java.util.{StringTokenizer}

object Closest {

  def closest(points: List[(Int, Int)]): Double = {

    def getDistance(p1: (Int, Int), p2: (Int, Int)): Double =
      math.sqrt(math.pow(p1._1 - p2._1, 2) + math.pow(p1._2 - p2._2, 2))

    def comparePoints(pts: List[(Int, Int)], minDistance: Double): Double =
      pts match {
        case Nil => minDistance
        case px if px.length <= 1 => minDistance
        case px if px.length > 1 => {
          val mid = px.length / 2
          val point = px(mid)
          val (lpoints, rpoints) = px splitAt mid
          //println("left " + lpoints)
          //println("right " + rpoints)
          val lminDistance = comparePoints(lpoints, minDistance)
          val rminDistance = comparePoints(rpoints, minDistance)

          var distance = getDistance(point, px.head)
          //println("left " + lpoints)
          //println("right " + rpoints)
          /*for (i <- 0 until lpoints.length; j <- 0 until rpoints.length) {
            val dist = getDistance(lpoints(i), rpoints(j))
            if (dist < distance)
              distance = dist
          }*/
          math.min(math.min(lminDistance, rminDistance), distance)
        }
      }

    val minDistance = comparePoints(points.sorted, Double.PositiveInfinity)
    BigDecimal(minDistance).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    def buildStartsEnds(n: Int, l: List[(Int, Int)]): List[(Int, Int)] =
      if (n == 0) l
      else buildStartsEnds(n - 1, (scanner.nextInt, scanner.nextInt) :: l)

    val points = buildStartsEnds(n, Nil)

    println(closest(points))
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
