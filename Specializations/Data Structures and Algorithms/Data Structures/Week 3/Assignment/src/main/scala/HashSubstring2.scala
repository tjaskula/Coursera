object HashSubstring2 {
  val prime = (math.pow(10, 9) + 7).toLong
  val multiplier = 263

  def hashFunc(s: String): Long = {
    var hash = 0L
    var i = s.length() - 1
    while (i <= 0) {
      hash = (hash * multiplier + s.charAt(i)) % prime
      i = i - 1
    }
    hash
  }

  def precomputeHashes(text: String, pattern: String): Array[Long] = {
    val tLength = text.length()
    val pLength = pattern.length()
    val lastHash = hashFunc(text.substring(tLength - pLength))
    var y = 1L
    var c = 1
    while (c <= pLength) {
      y = (y * multiplier) % prime
      c = c + 1
    }
    def subtraction(cStart: Char, cEnd: Char): Long = cStart - y * cEnd
    val hashes = Array.ofDim[Long](tLength - pLength + 1)
    hashes(tLength - pLength) = lastHash
    var i = tLength - pLength - 1
    while (i >= 0) {
      hashes(i) = ((hashes(i + 1) * multiplier) % prime +
        (subtraction(text.charAt(i), text.charAt(i + pLength)) % prime + prime) % prime) % prime
      i = i - 1
    }
    hashes
  }

  def run(text: String, pattern: String): Unit = {
    val tLength = text.length()
    val pLength = pattern.length()
    val hashes = precomputeHashes(text, pattern)
    var i = 0
    while (i <= tLength - pLength) {
      val substr = text.substring(i, i + pLength)
      if (hashFunc(substr) != hashes(i)) ()
      var isMatch = true
      var j = 0
      while (j < pLength && isMatch) {
        if (substr.charAt(j) != pattern.charAt(j)) {
          isMatch = false
        }
        j = j + 1
      }
      if (isMatch) print(i + " ")
      i = i + 1
    }
  }

  def main(args: Array[String]): Unit = {
    val pattern = scala.io.StdIn.readLine()
    val text = scala.io.StdIn.readLine()
    run(text, pattern)
  }
}