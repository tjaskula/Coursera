def product(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 1 else f(a) * product(f)(a + 1, b)

product(x => x)(1, 3)

def fact(n: Int) = product(x => x)(1, n)

fact(5)

def mapReduce(f: Int => Int, u: Int, combine:(Int, Int) => Int)(a: Int, b: Int): Int =
  if (a > b) u else combine(f(a), mapReduce(f, u, combine)(a + 1, b))

mapReduce(x => x * x, 0, (_+_))( 1, 3)

mapReduce(x => x, 1, (_*_))( 1, 3)

def product2(f: Int => Int)(a: Int, b: Int): Int =
  mapReduce(f, 1, (x, y) => x * y)(a, b)

product2(x => x)(1, 3)