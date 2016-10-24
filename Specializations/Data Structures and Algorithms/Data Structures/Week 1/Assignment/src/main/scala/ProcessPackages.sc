import scala.collection.immutable.Queue

val buffer = Queue[Int]()


def run(n: Int, bufferSize: Int)(read: Int => (Int, Int), write: Int => Unit): Unit = {
  def runIter(i: Int, finishTime: Int, qSize: Int, q: Queue[Int]): Unit = {
    if (i >= n) Unit
    else {
      val packet = read(i)
      if (buffer.isEmpty) {
        val enq = q.enqueue(finishTime + packet._2)
      }
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
run(5, 3)(i => p4(i), i => println(i))