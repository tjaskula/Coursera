def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}

last(List(1, 9, 10, 3))

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => Nil
  case y :: ys => y :: init(ys)
}

init(List(1, 9, 10, 3))

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y :: ys => reverse(ys) ++ List(y)
}

def flatten(xs: List[Any]): List[Any] = xs match {
  case Nil => Nil
  case (y: List[Any]) :: ys => flatten(y) ::: flatten(ys)
  case y :: ys => y :: flatten(ys)
}

flatten(List(List(1, 1), 2, List(3, List(5, 8)))) // List(1, 1, 2, 3, 5, 8)

def removeAt[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n + 1)

removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)

def removeAt2[T](n: Int, xs: List[T]): List[T] = {
  def removeIter[T](position: Int, xs: List[T], removed: List[T]): List[T] = xs match {
    case Nil => removed
    case y :: ys if position == n => removeIter(position + 1, ys, removed)
    case y :: ys => removeIter(position + 1, ys, removed ++ List(y))
  }
  removeIter(0, xs, Nil)
}

removeAt2(1, List('a', 'b', 'c', 'd')) // List(a, c, d)