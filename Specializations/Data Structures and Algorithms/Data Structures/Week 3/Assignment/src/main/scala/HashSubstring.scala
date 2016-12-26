object HashSubstring {
  val prime = (math.pow(10, 9) + 7).toLong
  val multiplier = 263

  def hashFunc(s: String): Long = {
    val hash = 0L
    (s foldRight hash) ((char, hashed) => (hashed * multiplier + char) % prime)
  }

  def precomputeHashes(text: String, pattern: String): Array[Long] = {
    val tLength = text.length()
    val pLength = pattern.length()
    val lastHash = hashFunc(text.substring(tLength - pLength))
    val y = ((1 to pLength) foldLeft 1L) ((acc, _) => (acc * multiplier) % prime)
    def subtraction(cStart: Char, cEnd: Char): Long = cStart - y * cEnd
    ((tLength - pLength - 1 to 0 by -1) foldLeft Array(lastHash)) ((h, i) => (((h.head * multiplier) % prime +
      (subtraction(text.charAt(i), text.charAt(i + pLength)) % prime + prime) % prime) % prime) +: h)
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
    val hashes = precomputeHashes(text, pattern)
    ((0 to tLength - pLength) foldLeft "") ((acc, i) => {
      val substr = text.substring(i, i + pLength)
      if (hashFunc(substr) != hashes(i)) acc
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