package observatory

import java.time.LocalDate

import observatory.utils.{Resources, SparkJob}
import utils.Resources._
import org.apache.spark.rdd.RDD

/**
  * 1st milestone: data extraction
  */
object Extraction extends SparkJob {

  import spark.implicits._

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    sparkLocateTemperatures(year, stationsFile, temperaturesFile).collect()
  }

  def sparkLocateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): RDD[(LocalDate, Location, Temperature)] = {
    val joined = joinedStationsAndTemperatures(readStations(stationsFile), readTemperatures(year, temperaturesFile))

    joined.map(p => (p._2._2.date, p._2._1.location, p._2._2.temperature))
  }

  def joinedStationsAndTemperatures(stations: RDD[(String, Station)], temperatures: RDD[(String, LocalizedTemperature)]): RDD[(String, (Station, LocalizedTemperature))] = {
    stations.join(temperatures)
  }

  def readStations(stationsFile: String): RDD[(String, Station)] = {
    def toStation(line: String): (String, Station) = {
      val (stn, wban, lat, lon) = line.split(",", -1) match { case Array(x, y, w, z) => (x, y, w, z) }
      val station = Station(stn, wban, lat, lon)
      (station.id, station)
    }

    spark.sparkContext.textFile(fsPath(stationsFile))
      .map(toStation)
      .filter(station => station._2.location.lat != 0.0 && station._2.location.lon != 0.0)
  }

  def readTemperatures(year: Year, temperaturesFile: String): RDD[(String, LocalizedTemperature)] = {
    def toLocalizedTemperature(line: String): (String, LocalizedTemperature) = {
      val (stn, wban, month, day, temp) = line.split(",", -1) match { case Array(x, y, v, w, z) => (x, y, v, w, z) }
      val lt = LocalizedTemperature(stn, wban, year, month, day, temp)
      (lt.id, lt)
    }

    spark.sparkContext.textFile(fsPath(temperaturesFile)).map(toLocalizedTemperature)
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    sparkAverageRecords(spark.sparkContext.parallelize(records.toSeq)).collect().toSeq
  }

  // Added method:
  def sparkAverageRecords(records: RDD[(LocalDate, Location, Temperature)]): RDD[(Location, Temperature)] = {
    records
      .map(record => (record._2, (record._3, 1.0)))
      .reduceByKey((r1, r2) => (r1._1 + r2._1, r1._2 + r2._2))
      .mapValues({case (temp, count) => temp / count})
  }
}
