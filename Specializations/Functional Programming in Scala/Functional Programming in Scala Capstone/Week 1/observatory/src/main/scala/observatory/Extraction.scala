package observatory

import java.nio.file.Paths
import java.time.LocalDate

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * 1st milestone: data extraction
  */
object Extraction {

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Observatory")
      .config("spark.master", "local")
      .getOrCreate()

  /** @return The filesystem path of the given resource */
  def fsPath(resource: String): String =
    Paths.get(getClass.getResource(resource).toURI).toString

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
    def toStationsPairs(line: String): ((String, String), Location) = {
      val (stn, wban, lat, lon) = line.split(",", -1) match { case Array(x, y, w, z) => (x, y, w, z) }
      ((stn, wban), Location(if (lat == "") 0.0 else lat.toDouble, if (lon == "") 0.0 else lon.toDouble))
    }

    def toTemperaturePairs(line: String): ((String, String), (Int, Int, Temperature)) = {
      val (stn, wban, month, day, temp) = line.split(",", -1) match { case Array(x, y, v, w, z) => (x, y, v, w, z) }
      ((stn, wban), (month.toInt, day.toInt, (temp.toDouble - 32) * (5.0/9.0)))
    }

    val stationsRdd = spark.sparkContext.textFile(fsPath(stationsFile))
      .map(toStationsPairs)
      .filter(stationPairs => stationPairs._2.lat != 0.0 && stationPairs._2.lon != 0.0)

    val temperaturesRdd = spark.sparkContext.textFile(fsPath(temperaturesFile)).map(toTemperaturePairs)

    stationsRdd
      .join(temperaturesRdd)
      .map(p => (LocalDate.of(year, p._2._2._1, p._2._2._2), p._2._1, p._2._2._3))
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
