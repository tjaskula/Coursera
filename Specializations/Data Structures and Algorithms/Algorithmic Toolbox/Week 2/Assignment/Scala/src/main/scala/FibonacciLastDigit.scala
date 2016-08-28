object FibonacciLastDigit {
  def fiboLastDigit(n: Int) : Long =
    if (n <= 1) n
    else {
      val res = fiboLastDigit(n - 1) + fiboLastDigit(n - 2)
      res % 10
    }

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine().toInt
    println(fiboLastDigit(input))
  }
}