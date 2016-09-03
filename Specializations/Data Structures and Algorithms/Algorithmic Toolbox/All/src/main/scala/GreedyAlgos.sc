def minRefils(x: List[Int], n: Int, l: Int): Int = {
  def minRefilsIter(numRefills: Int, currentRefill: Int): Int = {
    if (currentRefill <= n) {
      val lastRefill = currentRefill
      val newCurrentRefill = nextReachablePoint(currentRefill, lastRefill)
      if (newCurrentRefill == lastRefill) throw new Error("Impossible")
      else minRefilsIter(numRefills + 1, newCurrentRefill)
    }
    else numRefills
  }

  def nextReachablePoint(currentRefill: Int, lastRefill: Int): Int = {
    if (currentRefill <= n && x(currentRefill + 1) - x(lastRefill) <= l)
      nextReachablePoint(currentRefill + 1, lastRefill)
    else currentRefill
  }

  minRefilsIter(0, 0)
}

val x = List(0, 150, 300, 401, 420, 490, 500, 800)
val l = 400

minRefils(x, 6, l)