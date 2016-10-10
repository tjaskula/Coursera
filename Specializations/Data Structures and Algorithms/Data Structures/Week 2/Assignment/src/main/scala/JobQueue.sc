class JobQueue(val a: Array[Int], val size: Int, val swaps: StringBuilder = new StringBuilder("")) {


  def leftChild(i: Int) = 2 * i + 1

  def rightChild(i: Int) = 2 * i + 2

  def swap(i: Int, maxIndex: Int) = {
    val temp = a(i)
    a(i) = a(maxIndex)
    a(maxIndex) = temp
    swaps.append(i + " "+ maxIndex + "\n")
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

  def build(): String = {
    for (i <- size / 2 to 0 by -1) {
      siftDown(i)
    }
    ""
  }
}