import scala.util.{Random}

object RandomTreeGenerator {

  def main(args: Array[String]): Unit = {
    val r = Random
    r.setSeed(434)
    val n = r.nextInt(math.pow(10, 5).toInt)
    println(n)
  }
}