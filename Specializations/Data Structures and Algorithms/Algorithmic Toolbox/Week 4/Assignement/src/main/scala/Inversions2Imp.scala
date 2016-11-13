import java.io._
import java.util.StringTokenizer

object Inversions2Imp {

  def inv(list : Array[Int]) : Long = doInv(list)._1

  def doInv(list : Array[Int]) : (Long, Array[Int]) =
    if (list.length <= 1) {
      (0, list)
    } else {
      val (left, right) = list.splitAt(list.length / 2)
      val (leftCount, leftList) = doInv(left)
      val (rightCount, rightList) = doInv(right)
      val (mergeCount, mergeList) = doMerge(leftList, rightList)
      (leftCount + rightCount + mergeCount, mergeList)
    }

  def doMerge(left : Array[Int], right : Array[Int], count : Long = 0) : (Long, Array[Int]) =
    (left, right) match {
      case (Array(), r) => (count, r)
      case (l, Array()) => (count, l)
      case _ =>
        val lhead = left.head
        val ltail = left.tail
        val rhead = right.head
        val rtail = right.tail
        if (lhead <= rhead) {
          val (lcount, list) = doMerge(ltail, right, count)
          (count + lcount, lhead +: list)
        } else {
          val (rcount, list) = doMerge(left, rtail, count)
          (count + left.length + rcount, rhead +: list)
        }
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

    println(inv(a))

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