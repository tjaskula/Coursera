import java.io._
import java.util.StringTokenizer

import scala.collection.mutable.PriorityQueue

object JobQueue {

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val t: Int = scanner.nextInt
    val j: Int = scanner.nextInt

    object MinOrder extends Ordering[(Long, Long)] {
      def compare(x:(Long, Long), y:(Long, Long)) = {
        if (y._2 == x._2) y._1 compare x._1
        else y._2 compare x._2
      }
    }

    val a = Array.ofDim[(Long, Long)](j)

    val q = new PriorityQueue[(Long, Long)]()(MinOrder)

    val startTimes = Array.ofDim[Long](t)
    for (i <- 0 until t if i < j) {
      q.enqueue((i, scanner.nextLong))
      startTimes(i) = 0
      a(i) = (i, 0)
    }

    var i = t
    var c = 0
    while(i < j) {
      while (!q.isEmpty) {
        val (bestWorker, endTime) = q.dequeue()
        val start = startTimes(bestWorker.toInt)
        a(c) = (bestWorker, start)
        c = c + 1
        if (i < j) {
          q.enqueue((bestWorker, endTime + scanner.nextLong))
          startTimes(bestWorker.toInt) = endTime
          i = i + 1
        }
      }
    }
    a.sortWith((t1, t2) => if (t1._2 == t2._2) t1._1 < t2._1 else t1._2 < t2._2).foreach(t => println(t._1 + " " + t._2))
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
    def nextLong: Long = next.toLong
  }
}