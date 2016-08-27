object Fibonacci {
  def fibo(n: Int) : Long =
    if (n <= 1) n
    else fibo(n - 1) + fibo(n - 2)

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine().toInt
    println(fibo(input))
  }
}
