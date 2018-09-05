package barneshut

import java.awt._
import java.awt.event._
import javax.swing._
import javax.swing.event._
import scala.collection.parallel.{TaskSupport, defaultTaskSupport}

class SimulationModel {

  var screen = new Boundaries

  var bodies: Seq[Body] = Nil

  var quad: Quad = Empty(screen.centerX, screen.centerY, Float.MaxValue)

  var shouldRenderQuad = false

  var timeStats = new TimeStatistics

  var taskSupport: TaskSupport = defaultTaskSupport

  def initialize(parallelismLevel: Int, pattern: String, totalBodies: Int) {
    taskSupport = new collection.parallel.ForkJoinTaskSupport(
      new scala.concurrent.forkjoin.ForkJoinPool(parallelismLevel))

    pattern match {
      case "two-galaxies" => init2Galaxies(totalBodies)
      case _ => sys.error(s"no such initial pattern: $pattern")
    }
  }

  def init2Galaxies(totalBodies: Int) {
    val bodyArray = new Array[Body](totalBodies)
    val random = new scala.util.Random(213L)

    def galaxy(from: Int, num: Int, maxradius: Float, cx: Float, cy: Float, sx: Float, sy: Float) {
      val totalM = 1.5f * num
      val blackHoleM = 1.0f * num
      val cubmaxradius = maxradius * maxradius * maxradius
      for (i <- from until (from + num)) {
        val b = if (i == from) {
          new Body(blackHoleM, cx, cy, sx, sy)
        } else {
          val angle = random.nextFloat * 2 * math.Pi
          val radius = 25 + maxradius * random.nextFloat
          val starx = cx + radius * math.sin(angle).toFloat
          val stary = cy + radius * math.cos(angle).toFloat
          val speed = math.sqrt(gee * blackHoleM / radius + gee * totalM * radius * radius / cubmaxradius)
          val starspeedx = sx + (speed * math.sin(angle + math.Pi / 2)).toFloat
          val starspeedy = sy + (speed * math.cos(angle + math.Pi / 2)).toFloat
          val starmass = 1.0f + 1.0f * random.nextFloat
          new Body(starmass, starx, stary, starspeedx, starspeedy)
        }
        bodyArray(i) = b
      }
    }

    galaxy(0, bodyArray.length / 8, 300.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    galaxy(bodyArray.length / 8, bodyArray.length / 8 * 7, 350.0f, -1800.0f, -1200.0f, 0.0f, 0.0f)

    bodies = bodyArray.toSeq

    // compute center and boundaries
    screen = new Boundaries
    screen.minX = -2200.0f
    screen.minY = -1600.0f
    screen.maxX = 350.0f
    screen.maxY = 350.0f
  }

}
