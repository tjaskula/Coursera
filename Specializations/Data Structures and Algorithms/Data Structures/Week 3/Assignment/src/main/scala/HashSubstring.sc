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
    hashes(i) = (hashes(i + 1) * multiplier) + text.charAt(i) - (y * text.charAt(i + pLength)) % prime
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
    if (patternHash == hashes(i)) {
      var isMatch = true
      var j = 0
      while (j < pLength && isMatch) {
        isMatch = text.charAt(i + j) == pattern.charAt(j)
        j = j + 1
      }
      if (isMatch) sb.append(s"$i ")
    }
    i = i + 1
  }
  sb
}

run("abacaba", "aba", new StringBuilder()).toString()
run("testTesttesT", "Test", new StringBuilder()).toString()
run("baaaaaaa", "aaaaa", new StringBuilder()).toString()