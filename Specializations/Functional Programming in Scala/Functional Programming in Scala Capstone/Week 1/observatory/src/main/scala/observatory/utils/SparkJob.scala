package observatory.utils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

trait SparkJob {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  implicit lazy val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Observatory")
      .config("spark.master", "local")
      .getOrCreate()


  def readCsv(path: String): RDD[String] = spark.sparkContext.textFile(path)

  def sparkJob = this
}
