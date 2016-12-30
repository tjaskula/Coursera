object HashSubstring {
  val prime = (math.pow(10, 9) + 7).toLong
  val multiplier = 1

  def hashFunc(s: String): Long = {
    val hash = 0L
    (s foldRight hash) ((char, hashed) => (hashed * multiplier + char) % prime)
  }

  def precomputeHashes(text: String, pattern: String): Array[Long] = {
    val tLength = text.length()
    val pLength = pattern.length()
    val lastHash = hashFunc(text.substring(tLength - pLength))
    //println("last " + lastHash)
    val y = ((0 until pLength) foldLeft 1L) ((acc, _) => (acc * multiplier) % prime)
    ((tLength - pLength - 1 to 0 by -1) foldLeft Array(lastHash)) ((h, i) => ((h.head * multiplier) +
      text.charAt(i) - (y * text.charAt(i + pLength)) % prime) +: h)
  }

  def areEqual(substr: String, pattern: String): Boolean = {
    def toListOfChars(s: String): List[Char] =
      (s foldRight List.empty[Char]) ((next, acc) => next :: acc)

    def areEqualIter(xs: List[Char], ys: List[Char]): Boolean = xs match {
      case Nil => true
      case x::zs => if ( x != ys.head) false else areEqualIter(zs, ys.tail)
    }
    areEqualIter(toListOfChars(substr), toListOfChars(pattern))
  }

  def run(text: String, pattern: String): String = {
    val tLength = text.length()
    val pLength = pattern.length()
    val patternHash = hashFunc(pattern)
    val hashes = precomputeHashes(text, pattern)
    //println(hashes.mkString("|"))
    ((0 to tLength - pLength) foldLeft "") ((acc, i) => {
      val substr = text.substring(i, i + pLength)
      //println("Comparing - " + hashFunc(substr) + " | " + hashes(i))
      if (patternHash != hashes(i)) acc
      else if (areEqual(substr, pattern)) acc + " " + i
      else acc
    })
  }

  def main(args: Array[String]): Unit = {
    val pattern = scala.io.StdIn.readLine()
    val text = scala.io.StdIn.readLine()
    println(run(text, pattern))
  }
}