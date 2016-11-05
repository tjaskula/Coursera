object FibonacciAdvanced {
  def fibonacciMod(n: Long, m: Long) : BigInt = {
    def fibonacciModIter(n: Long, twoLast: List[BigInt]): BigInt = {
      val n2 = twoLast(0)
      val n1 = twoLast(1)
      if (n == 1) (n1 + n2) % m
      else fibonacciModIter(n - 1, List(n1, n1 + n2))
    }
    if (n <= 1) n
    else fibonacciModIter(n - 1, List(0, 1))
  }

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine()
    val tokens = input.split(" ").map(_.toLong)
    println(fibonacciMod(tokens(0), tokens(1)))
  }
}