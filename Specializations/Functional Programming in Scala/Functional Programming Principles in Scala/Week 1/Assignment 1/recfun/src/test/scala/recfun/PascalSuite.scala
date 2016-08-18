package recfun

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PascalSuite extends FunSuite {
  import Main.pascal
    test("pascal: col=0,row=2") {
      assert(pascal(0,2) === 1)
  }

    test("pascal: col=1,row=2") {
      assert(pascal(1,2) === 2)
  }

    test("pascal: col=1,row=3") {
      assert(pascal(1,3) === 3)
  }

    test("pascal: col=2,row=4") {
      assert(pascal(2,4) === 6)
  }

    test("pascal: col=3,row=5") {
      assert(pascal(3,5) === 10)
  }

    test("pascal: col=4,row=8") {
      assert(pascal(4,8) === 70)
  }

}
