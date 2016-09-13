def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())((t, l) => f(t) :: l)

def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0)((_, length) => length + 1)

val l = List(1, 2, 3, 4)
mapFun(l, (x: Int) => x * x)
lengthFun(l)