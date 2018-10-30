package observatory

import java.time.LocalDate

import observatory.utils.SparkJob
import org.scalatest.FunSuite
import Extraction._
import org.apache.spark.rdd.RDD

trait ExtractionTest extends FunSuite with SparkJob {
  val year = 1975
  val debug = false

  val stationsPath:String = "/stations.csv"
  val temperaturePath:String = s"/$year.csv"

  lazy val stations: RDD[((String, String), Station)] = readStations(stationsPath).persist
  lazy val temperatures: RDD[((String, String), LocalizedTemperature)] = readTemperatures(year, temperaturePath).persist
  // lazy val joinedV1:Dataset[StationDateLocationTemp] = Extraction.joinStationsWithTemperaturesV1(stationsV1, temperaturesV1).persist

  lazy val locateTemperatures = Extraction.locateTemperatures(year, stationsPath, temperaturePath)
  lazy val locateAverage = Extraction.locationYearlyAverageRecords(locateTemperatures)

  test("#1: stations") {
    if(debug) stations.foreach(println)
    assert(stations.filter(s => s._1._1 == "007005").count() === 0,"id: 007005")
    assert(stations.filter(s => s._1._1 == "007018").count() === 0,"id: 007018")
    assert(stations.filter(s => s._1._1 == "725346" && s._1._2 == "94866").count() === 1,"id: 725346,94866")
    assert(stations.filter(s => s._1._1 == "725346" && s._1._2 == "").count() === 1,"id: 725346")
    assert(stations.filter(s => s._1._2 == "68601").count() === 1,"id: 68601")
    assert(stations.count() === 27708,"Num stations")
  }

  test("#1: temperatures") {
    if(debug) temperatures.foreach(println)
    val count = temperatures.filter(t => t._1._1 == "010010").collect().length
    assert(count === 363,"id: 010010")
  }

  test("#1: 'locationYearlyAverageRecords' should work") {
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

  test("#1: locateTemperatures") {
    if(debug) locateTemperatures.take(20).foreach(println)
    assert(locateTemperatures.count(_._2 == Location(70.933, -8.667)) === 363)
    assert(locateTemperatures.size === 2176493)
  }

  test("#1: locationYearlyAverageRecords") {
    if(debug) locateAverage.take(20).foreach(println)
    assert(locateAverage.count(_._1 == Location(70.933, -8.667)) === 1)
    assert(locateAverage.size === 8251)
  }
}