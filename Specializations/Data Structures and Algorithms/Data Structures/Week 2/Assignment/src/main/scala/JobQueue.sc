class HeapBuilder(val a: Array[Int], var size: Int, val maxSize: Int) {

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

  def extractMin(): Int = {
    val result = a(0)
    a(0) = a(size)
    size = size - 1
    siftDown(0)
    result
  }

  def build(): Array[Int] = {
    for (i <- size / 2 to 0 by -1) {
      siftDown(i)
    }
    a
  }
}

val a1 = Array(1, 2, 3, 4, 5)
val hp1 = new HeapBuilder(a1, 4, 4)

val th1 = new HeapBuilder(Array(0, 1), 1, 1)

var assignedWorkers = scala.collection.mutable.Map[Int, List[Int]]()
var i = 0
while (!hp1.isEmpty) {
  if (assignedWorkers.contains(i)) {
    for (w <- 0 until assignedWorkers(i).length) {
      th1.insert(assignedWorkers(i)(w))
    }
  }
  while (!th1.isEmpty) {
    val bestWorker = th1.extractMin()
    val jobDuration = hp1.extractMin()
    var l: List[Int] = Nil
    if (assignedWorkers.contains(i + jobDuration)) {
      l = assignedWorkers(i + jobDuration)
    }
    assignedWorkers.put(i + jobDuration, bestWorker :: l)
    println(bestWorker + " " + i)
  }
  i = i + 1
}