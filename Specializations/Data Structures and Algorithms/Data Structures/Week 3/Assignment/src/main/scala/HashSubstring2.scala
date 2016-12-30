object HashSubstring2 {
  val prime = (math.pow(10, 9) + 7).toLong
  val multiplier = 1

  def hashFunc(s: String): Long = {
    var hash = 0L
    var i = s.length() - 1
    while (i >= 0) {
      hash = (hash * multiplier + s.charAt(i)) % prime
      i = i - 1
    }
    hash
  }

  def precomputeHashes(text: String, pattern: String): Array[Long] = {
    val tLength = text.length()
    val pLength = pattern.length()
    var y = 1L
    var c = 0
    while (c < pLength) {
      y = (y * multiplier) % prime
      c = c + 1
    }

    val hashes = Array.ofDim[Long](tLength - pLength + 1)
    val lastHash = hashFunc(text.substring(tLength - pLength))
    hashes(tLength - pLength) = lastHash
    var i = tLength - pLength - 1
    while (i >= 0) {
      hashes(i) = (hashes(i + 1) * multiplier + text.charAt(i) + prime) % prime - (y * text.charAt(i + pLength)) % prime
      i = i - 1
    }
    hashes
  }

  def run(text: String, pattern: String, sb: StringBuilder): StringBuilder = {
    val tLength = text.length()
    val pLength = pattern.length()
    val patternHash = hashFunc(pattern)
    val hashes = precomputeHashes(text, pattern)
    var i = 0
    while (i <= tLength - pLength) {
      //println("Pattern : " + patternHash)
      //println("Hash : " + hashes(i))
      if (patternHash == hashes(i)) {
        val substr = text.substring(i, i + pLength)
        /*var isMatch = true
        var j = 0
        while (j < pLength && isMatch) {
          isMatch = substr.charAt(j) == pattern.charAt(j)
          j = j + 1
        }*/
        if (substr == pattern) sb.append(i + " ")
      }
      i = i + 1
    }
    sb
  }

  def main(args: Array[String]): Unit = {
    val pattern = scala.io.StdIn.readLine()
    val text = scala.io.StdIn.readLine()

    val sb = run(text, pattern, new StringBuilder())
    println(sb.toString())
  }
}