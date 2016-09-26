def optimalWeight(knapsackWeight: Int, wt: Array[Int]): Int = {
  val res = Array.ofDim[Int](wt.length + 1, knapsackWeight + 1)
  for (i <- 0 to wt.length; w <- 0 to knapsackWeight) {
    res(i)(w) =
      if (i == 0 || w == 0)
        0
      else if (wt(i - 1) <= w)
        math.max(wt(i - 1) + res(i - 1)(w - wt(i - 1)), res(i - 1)(w))
      else
        res(i - 1)(w)
  }
  res(wt.length)(knapsackWeight)
}

optimalWeight(10, Array(1, 4, 8))