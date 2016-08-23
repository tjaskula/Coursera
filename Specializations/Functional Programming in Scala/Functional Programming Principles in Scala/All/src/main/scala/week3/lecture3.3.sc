trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty = true
  def head = throw new NoSuchElementException("Nil.head")
  def tail = throw new NoSuchElementException("Nil.tail")
}

def nth[T](n: Int, list: List[T]): T = {
  def iterList[T](n: Int, list: List[T], currentN: Int): T = {
    if (list.isEmpty) throw new IndexOutOfBoundsException("Empty list")
    else if (currentN == n) list.head
    else iterList(n, list.tail, currentN + 1)
  }
  iterList(n, list, 0)
}

def nth2[T](n: Int, l: List[T]): T =
  if (l.isEmpty) throw new IndexOutOfBoundsException("Empty list")
  else if (n == 0) l.head
  else nth2(n - 1, l.tail)

val l = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, new Nil[Int])))

nth(2, l)
nth2(2, l)