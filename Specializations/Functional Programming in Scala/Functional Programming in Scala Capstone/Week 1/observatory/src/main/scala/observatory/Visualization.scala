package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Observatory")
      .config("spark.master", "local")
      .getOrCreate()

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    sparkPredictTemperature(spark.sparkContext.parallelize(temperatures.toSeq), location)
  }

  def sparkPredictTemperature(temperatures: RDD[(Location, Temperature)], location: Location): Temperature = {

    def calculateDistanceInKilometer(temperaturesLocation: Location): Double = {
      val latDistance = Math.toRadians(temperaturesLocation.lat - location.lat)
      val lngDistance = Math.toRadians(temperaturesLocation.lon - location.lon)
      val sinLat = Math.sin(latDistance / 2)
      val sinLng = Math.sin(lngDistance / 2)
      val a = sinLat * sinLat +
        (Math.cos(Math.toRadians(temperaturesLocation.lat))
          * Math.cos(Math.toRadians(location.lat))
          * sinLng * sinLng)
      val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      (6371 * c).toInt
    }

    val predictions = temperatures.map(t => (calculateDistanceInKilometer(t._1), t._2)).collect()
    predictions.find(_._1 == 0.0) match {
      case Some((_, temp)) => temp
      case _ => inverseDistanceWeighted(predictions, power = 3)
    }
  }

  /**
    * https://en.wikipedia.org/wiki/Inverse_distance_weighting
    *
    * @param distanceTemperatureCombinations
    * @param power
    * @return
    */
  def inverseDistanceWeighted(distanceTemperatureCombinations: Iterable[(Double, Double)], power: Int): Double = {
    val (weightedSum, inverseWeightedSum) = distanceTemperatureCombinations
      .aggregate((0.0, 0.0))(
        {
          case ((ws, iws), (distance, temp)) => {
            val w = 1 / math.pow(distance, power)
            (w * temp + ws, w + iws)
          }
        }, {
          case ((wsA, iwsA), (wsB, iwsB)) => (wsA + wsB, iwsA + iwsB)
        }
      )

    weightedSum / inverseWeightedSum
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    ???
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {
    ???
  }

}

