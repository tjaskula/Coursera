import java.io._
import java.util.StringTokenizer

import scala.collection.mutable.PriorityQueue

object JobQueue2 {

  class MinHeap(val a: Array[(Long, Long)], var size: Int, val maxSize: Int) {

    def parent(i: Int) = i / 2

    def leftChild(i: Int) = 2 * i

    def rightChild(i: Int) = 2 * i + 1

    def isEmpty: Boolean = size == -1

    def structure: Array[(Long, Long)] = a

    def swap(i: Int, maxIndex: Int) = {
      val temp = a(i)
      a(i) = a(maxIndex)
      a(maxIndex) = temp
    }

    def compare(x:(Long, Long), y:(Long, Long)) = {
      if (y._2 == x._2) {
        if (y._1 > x._1) true
        else if (y._1 == x._1) false
        else false
      }
      else if (y._2 > x._2) true
      else false
    }

    def siftDown(i: Int): Unit = {
      val l = leftChild(i)
      val maxIndexL =
        if (l <= size && compare(a(l), a(i))) l else i
      val r = rightChild(i)
      val maxIndex =
        if (r <= size && compare(a(r), a(maxIndexL))) r else maxIndexL

      if (i != maxIndex) {
        swap(i, maxIndex)
        siftDown(maxIndex)
      }
    }

    def siftUp(i: Int): Unit = {
      //println(a.toList)
      var indx = i
      def isSwap() =
        if (a(parent(indx))._2 == a(indx)._2) {
          //println("parent v : " + a(parent(indx))._1 + " - a v : " + a(indx)._1)
          if (a(parent(indx))._1 > a(indx)._1)
            true
          else
            false
        }
        else if (a(parent(indx))._2 > a(indx)._2) true
        else false
      while (indx > -1 && isSwap()) {
        //println("swaping : " + parent(indx) + " - with : " + indx)
        swap(parent(indx), indx)
        indx = parent(indx)
      }
    }

    def insert(p: (Long, Long)): Unit = {
      if (size == maxSize) throw new IndexOutOfBoundsException("heap.maxsize.insert")
      else {
        size = size + 1
        a(size) = p
        siftUp(size)
      }
    }

    def extractMin(): (Long, Long) = {
      val result = a(0)
      a(0) = a(size)
      size = size - 1
      siftDown(0)
      result
    }

    def build(): Array[(Long, Long)] = {
      for (i <- size / 2 to 0 by -1) {
        siftDown(i)
      }
      a
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val t: Int = scanner.nextInt
    val j: Int = scanner.nextInt

    val a = Array.ofDim[(Long, Long)](j)

    val q = new MinHeap(Array.ofDim[(Long, Long)](j), -1, j - 1)

    val startTimes = Array.ofDim[Long](t)
    for (i <- 0 until t if i < j) {
      q.insert((i, scanner.nextLong))
      startTimes(i) = 0
      a(i) = (i, 0)
    }
    //println(q.structure.toList)
    var i = t
    var c = 0
    while(i < j) {
      while (!q.isEmpty) {
        val (bestWorker, endTime) = q.extractMin()
        val start = startTimes(bestWorker.toInt)
        a(c) = (bestWorker, start)
        c = c + 1
        if (i < j) {
          q.insert((bestWorker, endTime + scanner.nextLong))
          //println(q.structure.toList)
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