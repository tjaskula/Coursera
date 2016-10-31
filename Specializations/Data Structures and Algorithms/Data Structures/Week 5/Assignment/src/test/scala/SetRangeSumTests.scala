import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import SetRangeSum._

@RunWith(classOf[JUnitRunner])
class SetRangeSumTests extends FunSuite {

  def matchSum(t: SplayTree): Long = t match {
    case Empty() => 0
    case Node(_, s, _, _, _) => s
  }

  test("calculate Empty tree should return the same tree") {
    val t1 = new Empty()
    assert(calculateNodeSum(t1) === t1)
  }

  test("calculate Empty left tree should return sum of right tree and key") {
    val t1 = new Node(5, 4, Empty(), new Node(4, 4, Empty(), Empty(), Empty()), Empty())
    assert(matchSum(calculateNodeSum(t1)) === 9)
  }

  test("calculate Empty right tree should return sum of left tree and key") {
    val t1 = new Node(5, 4, new Node(4, 4, Empty(), Empty(), Empty()), Empty(), Empty())
    assert(matchSum(calculateNodeSum(t1)) === 9)
  }

  test("calculate tree should return sum of left, right tree and key") {
    val t1 = new Node(5, 4, new Node(4, 4, Empty(), Empty(), Empty()), new Node(4, 4, Empty(), Empty(), Empty()), Empty())
    assert(matchSum(calculateNodeSum(t1)) === 13)
  }
}