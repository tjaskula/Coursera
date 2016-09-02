def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y, isort(ys))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if(y >= x) x :: xs
                  else y :: insert(x,  ys)
}

val l = List(9, 7, 8, 2)
isort(l)