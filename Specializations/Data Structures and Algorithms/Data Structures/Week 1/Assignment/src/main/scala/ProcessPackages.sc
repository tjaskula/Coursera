import scala.collection.immutable.Queue

val buffer = Queue[Int]()

def addToBuffer(i: Int, n: Int, finishTime: Int, q: Queue[Int], bSize: Int)(read: Int => (Int, Int)): (Queue[Int], Int) = {
  def addToBufferIter(count: Int, arrivalTime: Int, finishTime: Int, q: Queue[Int], bSize: Int)(read: Int => (Int, Int)): (Queue[Int], Int) = {
    if (arrivalTime > finishTime || count >= n) (q, bSize)
    else {
      val packet = read(count)
      println("Reading packet: " + packet)
      val (newBuffer, newBufferSize) =
        if (q.length >= bSize) (q.enqueue(-1), bSize + 1)
        else (q.enqueue(finishTime + packet._2), bSize)
      addToBufferIter(count + 1, packet._1, packet._2, newBuffer, newBufferSize)(read)
    }
  }
  addToBufferIter(i, -1, finishTime, q, bSize)(read)
}

def processQueue(startTime: Int, q: Queue[Int], bSize: Int)(write: Int => Unit): (Queue[Int], Int, Int) = {
  if (q.isEmpty) (q, bSize, startTime)
  else {
    val (elem, dequed) = q.dequeue
    val (newFinishTime, deqSize) =
      if (elem == -1) {
        write(elem)
        (startTime, bSize - 1)
      }
      else {
        write(startTime)
        (elem, bSize)
      }
    processQueue(newFinishTime, dequed, deqSize)(write)
  }
}

def run(n: Int, bufferSize: Int)(read: Int => (Int, Int), write: Int => Unit): Unit = {
  def runIter(i: Int, finishTime: Int, qSize: Int, q: Queue[Int]): Unit = {
    if (i >= n) Unit
    else {
      val (enqued, enquedSize) = addToBuffer(i, n, finishTime, q, qSize)(read)
      val count = i + enqued.length
      println("count: " + count)
      val (dequed, dequedSize, newFinishTime) = processQueue(finishTime, enqued, enquedSize)(write)
      runIter(count, newFinishTime, dequedSize, dequed)
    }
  }
  runIter(0, 0, bufferSize, buffer)
}

run(0, 1)(i => (-1, -1), i => println(i))

val p1 = List((0, 0))
run(1, 1)(i => p1(i), i => println(i))

val p2 = List((0, 1), (0, 1))
run(2, 1)(i => p2(i), i => println(i))

val p3 = List((0, 1), (1, 1))
run(2, 1)(i => p3(i), i => println(i))

val p4 = List((0, 10), (0, 3), (6, 2), (7, 1), (7, 1))
//run(5, 3)(i => p4(i), i => println(i))