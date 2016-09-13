def squareList1(xs: List[Int]): List[Int] =
  xs match {
    case Nil => xs
    case y :: ys => y * y :: squareList1(ys)
  }

def squareList2(xs: List[Int]): List[Int] =
  xs map (e => e * e)

val l = List(1, 2, 3, 4)
squareList1(l)
squareList2(l)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (y, ys) = xs span  (x1 => x1 == x)
    y:: pack(ys)
}

pack(List("a", "a", "a", "b", "c", "c", "a"))


def encode[T](xs: List[T]): List[(T, Int)] = xs match {
  case Nil => Nil
  case x :: xs1 => pack(xs) map (l => (l.head, l.length))
}
encode(List("a", "a", "a", "b", "c", "c", "a"))