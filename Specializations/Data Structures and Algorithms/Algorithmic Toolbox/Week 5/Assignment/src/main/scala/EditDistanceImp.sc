import scala.math._

def minimum(i1: Int, i2: Int, i3: Int) = min(min(i1, i2), i3)
def distance(s1: String, s2: String) = {
  val dist = Array.tabulate(s2.length + 1, s1.length + 1) {
    (j, i) => if (j == 0) i else if (i == 0) j else 0
  }

  for(j <- 1 to s2.length; i <- 1 to s1.length) {
    val insertion = dist(j - 1)(i) + 1
    val deletion = dist(j)(i - 1) + 1
    val matching = dist(j - 1)(i - 1)
    val mismatch = dist(j - 1)(i - 1) + 1
    dist(j)(i) =
      if (s2(j - 1) == s1(i - 1)) minimum(insertion, deletion, matching)
      else minimum(insertion, deletion, mismatch)
  }
  dist(s2.length)(s1.length)
}

distance("ab", "ab")
distance("short", "ports")
distance("editing", "distance")