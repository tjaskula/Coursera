package observatory

import java.time.LocalDate

import org.scalatest.{FunSuite}

trait ExtractionTest extends FunSuite {
  def initializeExtraction(): Boolean =
    try {
      Extraction
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  test("'locationYearlyAverageRecords' should work") {
    assert(initializeExtraction(), " -- did you fill in all the values in Extraction (conf, sc, rdd)?")
    import Extraction._
    val res = locationYearlyAverageRecords(Seq(
      (LocalDate.of(2015, 8, 11), Location(37.35, -78.433), 27.3),
      (LocalDate.of(2015, 12, 6), Location(37.358, -78.438), 4.0),
      (LocalDate.of(2015, 1, 29), Location(37.358, -78.438), 2.0)
    ))
    assert(res == Seq(
      (Location(37.35, -78.433), 27.3),
      (Location(37.358, -78.438), 3.0)
    ).sortBy(_._2))
  }
}