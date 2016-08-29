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

gcd(18, 35)
gcd(14, 21)
gcd(28851538, 1183019)