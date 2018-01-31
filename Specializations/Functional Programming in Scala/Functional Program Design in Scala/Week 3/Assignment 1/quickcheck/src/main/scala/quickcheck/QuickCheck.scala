package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    element <- arbitrary[Int]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(element, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("getting smallest out of two elements") = forAll { (a: Int, b: Int) =>
    val aIsSmaller = a < b
    val heap = insert(b, insert(a, empty))
    if (aIsSmaller) findMin(heap) == a else findMin(heap) == b
  }

}
