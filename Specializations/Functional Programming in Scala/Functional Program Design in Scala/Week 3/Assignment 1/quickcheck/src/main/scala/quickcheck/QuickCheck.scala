package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  // example property
  property("empty") = forAll { a: Int =>
    val h = empty
    isEmpty(h)
  }

  lazy val genHeap: Gen[H] = for {
    element <- arbitrary[Int]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(element, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("Bogus1") = forAll { _: Unit =>
    val h1 = insert(1, empty)
    val h2 = insert(2, h1)
    val h3 = insert(3, h2)
    findMin(h3) == 1
  }

  property("Bogus2") = forAll { (a: Int, b: Int) =>
    val aIsSmaller = a < b
    val heap = insert(b, insert(a, empty))
    if (aIsSmaller) findMin(heap) == a else findMin(heap) == b
  }

  property("Bogus3") = forAll { (a: Int) =>
    val heap = insert(a, empty)
    deleteMin(heap) == empty
  }

  property("Bogus4") = forAll { (xs: List[Int]) =>
    def insertAll(xs: List[Int]): H = xs match {
      case Nil => empty
      case x::ys => insert(x, insertAll(ys))
    }
    def removeAll(h: H): List[Int] = {
      if (isEmpty(h)) Nil
      else {
        val min = findMin(h)
        min::removeAll(deleteMin(h))
      }
    }
    val h = insertAll(xs)
    val all = removeAll(h)
    all == xs.sorted
  }

  property("Bogus5") = forAll { (h1: H, h2: H) =>
    val minH1 = findMin(h1)
    val minH2 = findMin(h2)
    val newHeap = meld(h1, h2)
    val min =findMin(newHeap)
    min == minH1 || min == minH2
  }
}
