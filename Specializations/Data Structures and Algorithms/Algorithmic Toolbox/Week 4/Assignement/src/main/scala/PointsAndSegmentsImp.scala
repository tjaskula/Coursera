import java.io._
import java.util.{StringTokenizer}

object PointsAndSegmentsImp {

  def countSegments(starts: Array[Int], ends: Array[Int], points: Array[Int]): String = {

    val cnt = Array.ofDim[Int](points.length)

    /*val s = starts.sorted
    val e = ends.sorted

    for (i <- points.indices) {
      var index1 = 0
      s.indices.takeWhile(_ => s(index1) <= points(i)).foreach(_ => index1 += 1)

      var index2 = 0
      e.indices.takeWhile(_ => e(index2) < points(i)).foreach(_ => index2 += 1)


      cnt(i) = index1 - index2
    }

    cnt.mkString(" ")*/

    val s = starts.sorted
    val e = ends.sorted

    for (i <- points.indices) {
      var index1 = 0
      while (index1 < s.length && s(index1) <= points(i)) {
        index1 += 1
      }

      var index2 = 0
      while (index2 < e.length && e(index2) < points(i)) {
        index2 += 1
      }

      cnt(i) = index1 - index2
    }
    cnt.mkString(" ")
  }

  def main(args: Array[String]): Unit = {

    //new Thread(null, new Runnable() {
    //  def run() {
    //    try
          pointsSegments()

    //    catch {
    //      case e: IOException => {
    //      }
    //    }
    //  }
    //}, "1", 1 << 26).start()
  }

  def pointsSegments() = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt
    val m: Int = scanner.nextInt

    /*def buildStartsEnds(n: Int, l: (List[Int], List[Int])): (List[Int], List[Int]) =
      if (n == 0) l
      else buildStartsEnds(n - 1, (scanner.nextInt :: l._1, scanner.nextInt :: l._2))

    def buildPoints(m: Int, l: List[Int]): List[Int] =
      if (m == 0) l
      else scanner.nextInt :: buildPoints(m - 1, l)*/

    //val (starts, ends) = buildStartsEnds(n, (Nil, Nil))
    //val points = buildPoints(m, Nil)
    val starts: Array[Int] = new Array[Int](n)
    val ends: Array[Int] = new Array[Int](n)
    val points: Array[Int] = new Array[Int](m)
    var i: Int = 0
    while (i < n)
    {
      starts(i) = scanner.nextInt
      ends(i) = scanner.nextInt
      i += 1
    }
    i = 0
    while (i < m)
    {
      points(i) = scanner.nextInt
      i += 1
    }

    //countSegments(starts, ends, points)

    println(countSegments(starts, ends, points))
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
