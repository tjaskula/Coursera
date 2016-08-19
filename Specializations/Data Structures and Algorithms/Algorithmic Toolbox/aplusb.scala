object aplusb {
  def main(args: Array[String]) {
    val input = scala.io.StdIn.readLine()
    val tokens = input.split(" ").map(_.toInt)
    val a = tokens(0)
    val b = tokens(1)
    println(a + b)
  }
}
