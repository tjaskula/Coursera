abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

class Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  def union(other: IntSet): IntSet = other
  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem
  override def toString = "{" + left + elem + right + "}"
}

trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

object Nil extends List[Nothing] {
  def isEmpty = true
  def head = throw new NoSuchElementException("Nil.head")
  def tail = throw new NoSuchElementException("Nil.tail")
}

object List {
  def apply[T]() = Nil
  def apply[T](x: T): List[T] = new Cons(x, Nil)
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, Nil))
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

object test {
  val x: List[String] = Nil
  def f(xs: List[NonEmpty], x: Empty) = xs prepend x
}