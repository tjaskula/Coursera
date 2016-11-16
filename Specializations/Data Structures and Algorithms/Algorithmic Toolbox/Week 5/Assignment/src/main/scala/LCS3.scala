import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.util.StringTokenizer

object LCS3 {

  def lcs3Count(a: Array[Int], b: Array[Int], c: Array[Int]): Int = {
    val solution = Array.fill(a.length + 1, b.length + 1, c.length + 1)(0)

    for (i <- 1 to a.length; j <- 1 to b.length; k <- 1 to c.length) {
      if (a(i - 1) == b(j - 1) && a(i - 1) == c(k - 1)) solution(i)(j)(k) = solution(i - 1)(j - 1)(k - 1) + 1
      else solution(i)(j)(k) = Math.max(solution(i - 1)(j)(k), Math.max(solution(i)(j - 1)(k), solution(i)(j)(k - 1)))
    }

    //printArray(solution)

    solution(a.length)(b.length)(c.length)
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val an = scanner.nextInt
    val a = Array.ofDim[Int](an)
    for (i <- 0 until an) {
      a(i) = scanner.nextInt
    }

    val bn = scanner.nextInt
    val b = Array.ofDim[Int](bn)
    for (i <- 0 until bn) {
      b(i) = scanner.nextInt
    }

    val cn = scanner.nextInt
    val c = Array.ofDim[Int](cn)
    for (i <- 0 until cn) {
      c(i) = scanner.nextInt
    }

    println(lcs3Count(a, b, c))
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
    def nextChar: Char = next.head
  }
}
