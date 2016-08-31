def fibonacciMod(n: Long, m: Long) : BigInt = {
  def fibonacciModIter(n: Long, twoLast: List[BigInt]): BigInt = {
    val n2 = twoLast(0)
    val n1 = twoLast(1)
    if (n == 1) (n1 + n2) % m
    else fibonacciModIter(n - 1, List(n1, n1 + n2))
  }
  if (n <= 1) n
  else fibonacciModIter(n - 1, List(0, 1))
}

fibonacciMod(1, 239)
fibonacciMod(239, 1000)
//fibonacciMod(2816213588L, 30524)

def pisanoPeriod(m: BigInt): BigInt = {
  def pisanoPeriodIter(a: BigInt, b: BigInt, i: Long): Long =
    if (a == 0 && b == 1) i
    else pisanoPeriodIter(b, (a + b) % m, i + 1)

  pisanoPeriodIter(1, 1 % m, 1)
}

pisanoPeriod(2)
pisanoPeriod(3)
pisanoPeriod(7)
pisanoPeriod(10)
pisanoPeriod(11)
pisanoPeriod(12)
pisanoPeriod(1000)

def fibonacciModImp(n: BigInt, m: BigInt) : BigInt = {
  def fibonacciIter(n: BigInt, twoLast: List[BigInt]): BigInt = {
    val n2 = twoLast(0)
    val n1 = twoLast(1)
    if (n == 1) (n1 + n2) % m
    else fibonacciIter(n - 1, List(n1, n1 + n2))
  }
  val newN = n % pisanoPeriod(m)
  if (newN <= 1) newN
  else fibonacciIter(newN - 1, List(0, 1))
}

fibonacciModImp(1, 239)
fibonacciModImp(10, 2)
fibonacciModImp(239, 1000)
fibonacciModImp(2816213588L, 30524)