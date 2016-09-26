object PrimitiveCalculatorImp {

  def optimalSequence(n: Int): List[Int] = {

    def backtraceSolution(current: Int, steps: Array[Int], solution: List[Int]): List[Int] = {
      if (current > 1) {
        val newSolution = current :: solution
        val newCurrent =
          if (steps(current - 1) == steps(current) - 1)
            current - 1
          else if (current % 2 == 0 && (steps(current / 2) == steps(current) - 1))
            current / 2
          else if (current % 3 == 0 && (steps(current / 3) == steps(current) - 1))
            current / 3
          else throw new Exception("current")
        backtraceSolution(newCurrent, steps, newSolution)
      }
      else 1 :: solution
    }

    def constructSolution(current: Int, steps: Array[Int]): Array[Int] = {
      if (current > n) steps
      else {
        steps(current) = steps(current - 1) + 1
        if (current % 2 == 0) steps(current) = math.min(steps(current), steps(current / 2) + 1)
        if (current % 3 == 0) steps(current) = math.min(steps(current), steps(current / 3) + 1)
        constructSolution(current + 1, steps)
      }
    }
    val steps = constructSolution(1, new Array(n + 1))
    backtraceSolution(n, steps, Nil)
  }

  def main(args: Array[String]): Unit = {
    val n = scala.io.StdIn.readLine().toInt
    val res = optimalSequence(n)
    println(res.length - 1)
    println(res mkString " ")
  }
}