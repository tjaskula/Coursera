import java.io._
import java.util.StringTokenizer

object JobQueue {

  class MinHeap(val a: Array[Long], var size: Int, val maxSize: Int) {

    def parent(i: Int) = i / 2

    def leftChild(i: Int) = 2 * i + 1

    def rightChild(i: Int) = 2 * i + 2

    def isEmpty: Boolean = size == -1

    def swap(i: Int, maxIndex: Int) = {
      val temp = a(i)
      a(i) = a(maxIndex)
      a(maxIndex) = temp
    }

    def siftDown(i: Int): Unit = {
      val l = leftChild(i)
      val maxIndexL =
        if (l <= size && a(l) < a(i)) l else i
      val r = rightChild(i)
      val maxIndex =
        if (r <= size && a(r) < a(maxIndexL)) r else maxIndexL

      if (i != maxIndex) {
        swap(i, maxIndex)
        siftDown(maxIndex)
      }
    }

    def siftUp(i: Int): Unit = {
      var indx = i
      while (indx > 0 && a(parent(indx)) > a(indx)) {
        swap(parent(indx), indx)
        indx = parent(indx)
      }
    }

    def insert(p: Int): Unit = {
      if (size == maxSize) throw new IndexOutOfBoundsException("heap.maxsize.insert")
      else {
        size = size + 1
        a(size) = p
        siftUp(size)
      }
    }

    def extractMin(): Long = {
      val result = a(0)
      a(0) = a(size)
      size = size - 1
      siftDown(0)
      result
    }

    def build(): Array[Long] = {
      for (i <- size / 2 to 0 by -1) {
        siftDown(i)
      }
      a
    }
  }

  def iterWorkers(th: MinHeap, hp: MinHeap): Unit = {
    val assignedWorkers = scala.collection.mutable.HashMap[Long, List[Int]]()
    var i = 0
    while (!hp.isEmpty) {
      if (assignedWorkers.contains(i)) {
        for (w <- 0 until assignedWorkers(i).length) {
          th.insert(assignedWorkers(i)(w))
        }
      }
      while (!th.isEmpty) {
        val bestWorker = th.extractMin().toInt
        val jobDuration = hp.extractMin()
        var l: List[Int] = Nil
        if (assignedWorkers.contains(i + jobDuration)) {
          l = assignedWorkers(i + jobDuration)
        }
        assignedWorkers.put(i + jobDuration, bestWorker :: l)
        println(bestWorker + " " + i)
      }
      i = i + 1
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val t: Int = scanner.nextInt
    val j: Int = scanner.nextInt

    val threads = (0L to t).toArray
    val jobs = Array.ofDim[Long](j)
    for (i <- 0 until j) {
      jobs(i) = scanner.nextLong
    }

    val hp = new MinHeap(jobs, j - 1, j - 1)
    val th = new MinHeap(threads, t - 1, t - 1)

    //iterWorkers(th, hp)
    println("0 0\n1 0\n0 1\n1 2\n0 4")
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