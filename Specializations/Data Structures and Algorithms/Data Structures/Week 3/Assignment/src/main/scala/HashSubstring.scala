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
    val y = ((0 until pLength) foldLeft 1L) ((acc, _) => (acc * multiplier) % prime)
    ((tLength - pLength - 1 to 0 by -1) foldLeft Array(lastHash)) ((h, i) => ((h.head * multiplier) +
      text.charAt(i) - (y * text.charAt(i + pLength)) % prime) +: h)
  }

  def areEqual(text: String, pattern: String, index: Int): Boolean = {
    def areEqualIter(i: Int): Boolean = {
      if (i >= pattern.length()) true
      else {
        if (pattern.charAt(i) != text.charAt(index + i)) false else areEqualIter(i + 1)
      }
    }
    areEqualIter(0)
  }

  def run(text: String, pattern: String): StringBuilder = {
    val tLength = text.length()
    val pLength = pattern.length()
    val patternHash = hashFunc(pattern)
    val hashes = precomputeHashes(text, pattern)
    ((0 to tLength - pLength) foldLeft new StringBuilder()) ((acc, i) => {
      if (patternHash != hashes(i)) acc
      else if (areEqual(text, pattern , i)) acc.append(s"$i ")
      else acc
    })
  }

  def main(args: Array[String]): Unit = {
    val pattern = scala.io.StdIn.readLine()
    val text = scala.io.StdIn.readLine()
    println(run(text, pattern))
  }
}