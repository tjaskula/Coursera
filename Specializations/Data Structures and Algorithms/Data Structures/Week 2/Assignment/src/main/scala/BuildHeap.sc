class BuildHeap(val a: Array[Int], val size: Int) {

  def leftChild(i: Int) = 2 * i

  def rightChild(i: Int) = 2 * i + 1

  def swap(i: Int, maxIndex: Int) = {
    val temp = a(i)
    a(i) = a(maxIndex)
    a(maxIndex) = temp
  }

  def siftDown(i: Int): Unit = {
    val l = leftChild(i)
    val r = rightChild(i)
    val maxIndexL =
      if (l <= size && a(l) < a(i)) l else i
    val maxIndex =
      if (r <= size && a(r) < a(maxIndexL)) r else maxIndexL

    if (i != maxIndex) {
      swap(i, maxIndex)
      siftDown(maxIndex)
    }
  }

  def build(): Array[Int] = {
    for (i <- size / 2 to 0 by -1) {
      siftDown(i)
    }
    a
  }
}

val a1 = Array(5, 4, 3, 2, 1)
new BuildHeap(a1, 4).build().toList

val a2 = Array(1, 2, 3, 4, 5)
new BuildHeap(a2, 4).build().toList