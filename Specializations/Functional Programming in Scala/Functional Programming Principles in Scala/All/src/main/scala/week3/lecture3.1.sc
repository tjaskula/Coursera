abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

object Empty extends IntSet {
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

val t1 = new NonEmpty(3, Empty, Empty)
val t2 = t1 incl 4
val t3 = t2 incl 2
val t4 = new NonEmpty(5, Empty, new NonEmpty(6, Empty, Empty))
val t5 = t3 union t4