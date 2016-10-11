import scala.math.Ordering.Implicits._
import scala.collection.mutable.PriorityQueue

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

object MinOrder extends Ordering[(Int, Int)] {
  def compare(x:(Int, Int), y:(Int, Int)) = y._2 compare x._2
}

val busyThreads = new PriorityQueue[(Long, Long)]()(MinOrder)
val nQueue = busyThreads.takeWhile(t => t._2 == 1L)

def iterWorkers(th: MinHeap, hp: MinHeap, threads: Int): Unit = {
  val busyThreads = Array.ofDim[Long](threads)
  var i = 0
  while (!hp.isEmpty) {
    if (busyThreads())
    while (!th.isEmpty) {
      val bestWorker = th.extractMin().toInt
      val jobDuration = hp.extractMin()
      busyThreads(bestWorker) = i + jobDuration
      println(bestWorker + " " + i)
    }
    i = i + 1
  }
}

val a1 = Array[Long](1, 2, 3, 4, 5)
val hp1 = new MinHeap(a1, 4, 4)
val th1 = new MinHeap(Array[Long](0, 1), 1, 1)

iterWorkers(th1, hp1, 2)

val a2 = Array[Long](1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
val t2 = Array[Long](0, 1, 2, 3)

val hp2 = new MinHeap(a2, 19, 19)
val th2 = new MinHeap(t2, 3, 3)

iterWorkers(th2, hp2, 4)