import scala.math.Ordering.Implicits._
import scala.collection.mutable.PriorityQueue

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
      else true
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
    var indx = i
    def isSwap() =
      if (a(parent(indx))._2 == a(indx)._2) {
        if (a(parent(indx))._1 > a(indx)._1)
          true
        else
          false
      }
      else if (a(parent(indx))._2 > a(indx)._2) true
      else false
    while (indx > 0 && isSwap()) {
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

object MinOrder extends Ordering[(Long, Long)] {
  def compare(x:(Long, Long), y:(Long, Long)) = {
    if (y._2 < x._2) -1
    else if (y._2 == x._2) {
      if (y._1 < x._1) -1
      else if (y._1 == x._1) 0
      else 1
    }
    else 1
  }
}

val q1 = new PriorityQueue[(Long, Long)]()(MinOrder)

var j = 5

val r1 = Array[Long](1, 2, 3, 4, 5)
var a1 = Array.ofDim[(Long, Long)](j)
val hp1 = new MinHeap(Array.ofDim[(Long, Long)](5), -1, 4)
var startTimes = Array.ofDim[Long](2)
for (i <- 0 until 2) {
  q1.enqueue((i, r1(i)))
  hp1.insert((i, r1(i)))
  startTimes(i) = 0
}

var i = 2
var c = 0
while(i < j) {
  while (!hp1.isEmpty) {
    val (bestWorker, endTime) = hp1.extractMin()
    val start = startTimes(bestWorker.toInt)
    a1(c) = (bestWorker, start)
    c = c + 1
    if (i < j) {
      hp1.insert((bestWorker, endTime + r1(c + 1)))
      startTimes(bestWorker.toInt) = endTime
      i = i + 1
    }
  }
}

a1.sortWith((t1, t2) => if (t1._2 == t2._2) t1._1 < t2._1 else t1._2 < t2._2).foreach(t => println(t._1 + " " + t._2))

a1 = Array.ofDim[(Long, Long)](j)
startTimes = Array.ofDim[Long](2)

i = 2
c = 0
while(i < j) {
  while (!q1.isEmpty) {
    val (bestWorker, endTime) = q1.dequeue()
    val start = startTimes(bestWorker.toInt)
    a1(c) = (bestWorker, start)
    c = c + 1
    if (i < j) {
      q1.enqueue((bestWorker, endTime + r1(c + 1)))
      startTimes(bestWorker.toInt) = endTime
      i = i + 1
    }
  }
}

a1.sortWith((t1, t2) => if (t1._2 == t2._2) t1._1 < t2._1 else t1._2 < t2._2).foreach(t => println(t._1 + " " + t._2))

j = 20
val q2 = new PriorityQueue[(Long, Long)]()(MinOrder)

val r2 = Array[Long](1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
var a2 = Array.ofDim[(Long, Long)](20)
val hp2 = new MinHeap(Array.ofDim[(Long, Long)](20), -1, 19)
var startTimes2 = Array.ofDim[Long](4)
for (i <- 0 until 4) {
  q2.enqueue((i, r2(i)))
  hp2.insert((i, r2(i)))
  startTimes2(i) = 0
}

i = 4
c = 0
while(i < j) {
  while (!hp2.isEmpty) {
    val (bestWorker, endTime) = hp2.extractMin()
    val start = startTimes2(bestWorker.toInt)
    a2(c) = (bestWorker, start)
    c = c + 1
    if (i < j) {
      hp2.insert((bestWorker, endTime + r2(c)))
      startTimes2(bestWorker.toInt) = endTime
      i = i + 1
    }
  }
}

a2.sortWith((t1, t2) => if (t1._2 == t2._2) t1._1 < t2._1 else t1._2 < t2._2).foreach(t => println(t._1 + " " + t._2))

a2 = Array.ofDim[(Long, Long)](j)
startTimes2 = Array.ofDim[Long](4)

i = 4
c = 0
while(i < j) {
  while (!q2.isEmpty) {
    val (bestWorker, endTime) = q2.dequeue()
    val start = startTimes2(bestWorker.toInt)
    a2(c) = (bestWorker, start)
    c = c + 1
    if (i < j) {
      q2.enqueue((bestWorker, endTime + r2(c)))
      startTimes2(bestWorker.toInt) = endTime
      i = i + 1
    }
  }
}

a2.sortWith((t1, t2) => if (t1._2 == t2._2) t1._1 < t2._1 else t1._2 < t2._2).foreach(t => println(t._1 + " " + t._2))