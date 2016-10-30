import scala.util.Random

def time[A](f: => A) = {
  val s = System.nanoTime
  val ret = f
  println("time: "+(System.nanoTime-s)/1e6+"ms")
  ret
}

val l1 = 10
val l2 = List(7, 5, 14, 2, 8, 8, 10, 1, 2, 3)

val l3 = List(0, 1)

val l4 = List(1L)

val l5 = List(100000L, 90000L)


def remove(num: Long, list: List[Long]) = list diff List(num)

def findTwoMax(l: List[Long]) : List[Long] = {
  def findTwoMaxInner(l: List[Long], product: List[Long]) : List[Long] = product match {
    case _ if l.isEmpty => product
    case List(x1, x2) => product
    case p => val m = l.max; findTwoMaxInner (remove(m, l), product ::: List(m))
  }
  findTwoMaxInner(l, List())
}

def findTwoMax2(l: List[Long]) : List[Long] = {
  def findTwoMaxInner(l: List[Long], products: List[Long]) : List[Long] = products match {
    case _ if l.isEmpty => products
    case List(x1, x2) => products
    case p => val m = l.max; findTwoMaxInner (remove(m, l), m :: products)
  }
  findTwoMaxInner(l, List())
}

val twoMax = findTwoMax(l4)
twoMax.product

val r = Random
r.setSeed(345L)
val l6 = List.fill(1000000)(r.nextInt(Int.MaxValue).toLong)
val twoMax2 = time(findTwoMax(l6))
twoMax2.product

val twoMax3 = time(findTwoMax2(l6))
twoMax3.product
