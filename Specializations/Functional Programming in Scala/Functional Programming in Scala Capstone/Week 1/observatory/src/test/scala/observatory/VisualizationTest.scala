package observatory


import java.time.LocalDate

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

trait VisualizationTest extends FunSuite with Checkers {
  def initialize(): Boolean =
    try {
      Visualization
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  test("'predictTemperature' predicted temperature at location z should be closer to known temperature at location x than to known temperature at location y, if z is closer (in distance) to x than y, and vice versa") {
    assert(initialize(), " -- did you fill in all the values in Extraction (conf, sc, rdd)?")
    import Visualization._
    val res = predictTemperature(Seq(
      (Location(10, 10), 10),
      (Location(50, 50), 50)
    ), Location(0.0, 0.0))
    assert(res == 10.0)
  }
}
