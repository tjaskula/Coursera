object Lcm {
  def lcm(a: Long, b: Long): Long = {
    def lcmIter(a: Long, b: Long, l: Long): Long = {
      if (l > a * b) a * b
      else if (l % a == 0 && l % b == 0) l
      else lcmIter(a, b, l + 1)
    }
    lcmIter(a, b, 1)
  }

  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine()
    val tokens = input.split(" ").map(_.toInt)
    println(lcm(tokens(0), tokens(1)))
  }
}
