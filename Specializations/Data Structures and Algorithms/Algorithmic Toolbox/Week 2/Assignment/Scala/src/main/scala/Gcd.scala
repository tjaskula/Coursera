object Gcd {
  def gcd(a: Int, b: Int): Int = {
    def gcdIter(a: Int, b: Int, d: Int, biggest: Int): Int = {
      if (d > a || d > b) biggest
      else {
        val newBiggest = if (a % d == 0 && b % d == 0 && d > biggest) d
                         else biggest
        gcdIter(a, b, d + 1, newBiggest)
      }
    }
    gcdIter(a, b, 2, 1)
  }

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine()
    val tokens = input.split(" ").map(_.toInt)
    println(gcd(tokens(0), tokens(1)))
  }
}