def lcm(a: Long, b: Long): Long = {
  def lcmIter(a: Long, b: Long, l: Long): Long = {
    if (l > a * b) a * b
    else if (l % a == 0 && l % b == 0) l
    else lcmIter(a, b, l + 1)
  }
  lcmIter(a, b, 1)
}

lcm(6, 8)
lcm(28851538, 1183019)