import scala.math.Ordering.Implicits._
import scala.collection.mutable.PriorityQueue

class MinHeap(val a: Array[(Long, Long)], var size: Int, val maxSize: Int) {

  def parent(i: Int) = i / 2

  def leftChild(i: Int) = 2 * i + 1

  def rightChild(i: Int) = 2 * i + 2

  def isEmpty: Boolean = size == -1

  def structure: List[(Long, Long)] = a.toList

  def swap(i: Int, maxIndex: Int) = {
    val temp = a(i)
    a(i) = a(maxIndex)
    a(maxIndex) = temp
  }

  def siftDown(i: Int): Unit = {
    val l = leftChild(i)
    val maxIndexL =
      if (l <= size && a(l)._2 <= a(i)._2) l else i
    val r = rightChild(i)
    val maxIndex =
      if (r <= size && a(r)._2 <= a(maxIndexL)._2) r else maxIndexL

    if (i != maxIndex) {
      swap(i, maxIndex)
      siftDown(maxIndex)
    }
  }

  def siftUp(i: Int): Unit = {
    var indx = i
    while (indx > 0 && a(parent(indx))._2 >= a(indx)._2) {
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

val a1 = Array[Long](1, 2, 3, 4, 5)
//val hp1 = new MinHeap(Array.ofDim[(Long, Long)](5), -1, 4)
val startTimes = Array.ofDim[Long](2)
for (i <- 0 until 2) {
  q1.enqueue((i, a1(i)))
  //hp1.insert((i, a1(i)))
  startTimes(i) = 0
}

var i = 2
while(i < 5) {
  //println("i:" + i)
  //println(startTimes.toList)
  //val (bestWorker, endTime) = hp1.extractMin()
  while(!q1.isEmpty) {
    val (bestWorker, endTime) = q1.dequeue()
    val start = startTimes(bestWorker.toInt)
    print(bestWorker + " " + start + "\n")
    val nextJob = i
    if (nextJob < 5) {
      //println("bestWorker :" + bestWorker + "   endTime: " + endTime + "   nextTime : " + a1(nextJob))
      //println("enqueuing (worker : " + bestWorker + "   time : " + (endTime + a1(nextJob)) + ")")
      q1.enqueue((bestWorker, endTime + a1(nextJob)))
      startTimes(bestWorker.toInt) = endTime
      i = i + 1
    }
  }
  //print(bestWorker + " " + start + "\n")
  //hp1.insert(bestWorker, start + a1(i))
  //startTimes(bestWorker.toInt) = start + endTime
}
//while (!hp1.isEmpty) {
//while (!q1.isEmpty) {
//  //val (bestWorker, endTime) = hp1.extractMin()
//  val (bestWorker, endTime) = q1.dequeue()
//  val start = startTimes(bestWorker.toInt)
//  print(bestWorker + " " + start + "\n")
//}

val q2 = new PriorityQueue[(Long, Long)]()(MinOrder)

val a2 = Array[Long](1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
//val hp2 = new MinHeap(Array.ofDim[(Long, Long)](20), -1, 19)
val startTimes2 = Array.ofDim[Long](4)
for (i <- 0 until 4) {
  q2.enqueue((i, a2(i)))
  //hp2.insert((i, a2(i)))
  startTimes2(i) = 0
}
//
i = 4
while(i < 20) {
  //  val (bestWorker, endTime) = hp2.extractMin()
  while (!q2.isEmpty) {
    val (bestWorker, endTime) = q2.dequeue()
    val start = startTimes2(bestWorker.toInt)
    print(bestWorker + " " + start + "\n")
    val nextJob = i
    if (nextJob < 20) {
      //println("bestWorker :" + bestWorker + "   endTime: " + endTime + "   nextTime : " + a1(nextJob))
      //println("enqueuing (worker : " + bestWorker + "   time : " + (endTime + a1(nextJob)) + ")")
      q2.enqueue((bestWorker, endTime + a2(nextJob)))
      startTimes2(bestWorker.toInt) = endTime
      i = i + 1
    }
    //  val start = startTimes2(bestWorker.toInt)
    //  print(bestWorker + " " + start + "\n")
    //  hp2.insert(bestWorker, start + a2(i))
    //  startTimes2(bestWorker.toInt) = start + endTime
    //  //if (i % 4 == 0)
    //    i = i + 1
  }
}
//while (!hp2.isEmpty) {
//  val (bestWorker, endTime) = hp2.extractMin()
//  val start = startTimes2(bestWorker.toInt)
//  print(bestWorker + " " + start + "\n")
//}