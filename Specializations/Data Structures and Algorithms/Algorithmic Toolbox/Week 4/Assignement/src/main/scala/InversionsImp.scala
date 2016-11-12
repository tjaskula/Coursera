import java.io._
import java.util.StringTokenizer

object InversionsImp {

  def merge(a: Array[Int], aux: Array[Int], lo: Int, mid: Int, hi: Int): Long = {
    def mergeBack(a: Array[Int], aux: Array[Int], k: Int, i: Int, j: Int, inversions: Long): Long = {
      if (k > hi) inversions
      else {
        val (newInversions, newI, newJ) =
          if (i > mid) {
            a(k) = aux(j)
            (inversions, i, j + 1)
          }
          else if (j > hi) {
            a(k) = aux(i)
            (inversions, i + 1, j)
          }
          else if (aux(j) < aux(i)) {
            a(k) = aux(j)
            (inversions + (mid - i + 1), i, j + 1)
          }
          else {
            a(k) = aux(i)
            (inversions, i + 1, j)
          }
        mergeBack(a, aux, k + 1, newI, newJ, newInversions)
      }
    }

    Array.copy(a, lo, aux, lo, hi - lo + 1)
    mergeBack(a, aux, lo, lo, mid + 1, 0)
  }

  def countMerge(a: Array[Int], b: Array[Int], aux: Array[Int], lo: Int, hi: Int, inversions: Long): Long = {
    if (hi <= lo) 0
    else {
      val mid = lo + (hi - lo) / 2
      val inversionsLeft = countMerge(a, b, aux, lo, mid, 0)
      val inversionsRight = countMerge(a, b, aux, mid + 1, hi, 0)
      val inversionsMerge = merge(b, aux, lo, mid, hi)
      inversionsLeft + inversionsRight + inversionsMerge
    }
  }

  def count(a: Array[Int]): Long = {
    val b = Array.ofDim[Int](a.length)
    val aux = Array.ofDim[Int](a.length)
    Array.copy(a, 0, b, 0, a.length)
    countMerge(a, b, aux, 0, a.length - 1, 0)
  }

  def main(args: Array[String]): Unit = {

    new Thread(null, new Runnable() {
      def run() {
        try
          runInversions()

        catch {
          case e: IOException => {
          }
        }
      }
    }, "1", 1 << 26).start()
  }

  def runInversions() = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    val a = Array.ofDim[Int](n)

    for (i <- 0 until n) {
      a(i) = scanner.nextInt
    }

    println(count(a))

    /*def buildList(n: Int, l: List[Int]): List[Int] =
      if (n == 0) l
      else scanner.nextInt :: buildList(n - 1, l)

    val a = buildList(n, Nil)

    println(msort(a)._1)*/
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
