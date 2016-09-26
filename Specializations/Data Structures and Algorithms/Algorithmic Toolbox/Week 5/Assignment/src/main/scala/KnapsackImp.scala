import java.io._
import java.util.{StringTokenizer}

object KnapsackImp {
  def optimalWeight(knapsackWeight: Int, weights: Array[Int]): Int = {
    val solution = Array.ofDim[Int](weights.length + 1, knapsackWeight + 1)
    for (i <- 0 to weights.length; w <- 0 to knapsackWeight) {
      solution(i)(w) =
        if (i == 0 || w == 0)
          0
        else if (weights(i - 1) <= w)
          math.max(weights(i - 1) + solution(i - 1)(w - weights(i - 1)), solution(i - 1)(w))
        else
          solution(i - 1)(w)
    }
    solution(weights.length)(knapsackWeight)
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val w: Int = scanner.nextInt
    val n: Int = scanner.nextInt
    val a: Array[Int] = new Array[Int](n)
    var i: Int = 0

    while (i < n) {
      a(i) = scanner.nextInt
      i = i + 1
    }
    println(optimalWeight(w, a))
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