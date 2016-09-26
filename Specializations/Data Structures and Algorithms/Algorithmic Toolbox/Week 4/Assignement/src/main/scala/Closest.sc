object Cl {
  def closest(points: List[(Int, Int)]): Double = {

    def getDistance(p1: (Int, Int), p2: (Int, Int)): Double =
      math.sqrt(math.pow(p1._1 - p2._1, 2) + math.pow(p1._2 - p2._2, 2))

    def comparePoints(point: (Int, Int), pts: List[(Int, Int)], minDistance: Double): Double =
      pts match {
        case Nil => minDistance
        case p :: px => {
          val mid = px.length / 2
          val (lpoints, rpoints) = px splitAt mid
          val lminDistance = comparePoints(point, lpoints, minDistance)
          val rminDistance = comparePoints(point, rpoints, minDistance)
          val calculatedDistance = getDistance(point, p)
          math.min(math.min(lminDistance, rminDistance), calculatedDistance)
        }
      }

    def closestIter(points: List[(Int, Int)], minDistance: Double): Double =
      points match {
        case Nil => BigDecimal(minDistance).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
        case p :: px => {
          closestIter(px, comparePoints(p, px, minDistance))
        }
      }
    closestIter(points, Double.PositiveInfinity)
  }
}

def closest(points: List[(Int, Int)]): Double = {

  def getDistance(p1: (Int, Int), p2: (Int, Int)): Double =
    math.sqrt(math.pow(p1._1 - p2._1, 2) + math.pow(p1._2 - p2._2, 2))

  def comparePoints(pts: List[(Int, Int)], minDistance: Double): Double =
    pts match {
      case Nil => minDistance
      case px if px.length <= 1 => minDistance
      case px if px.length > 1 => {
        val mid = px.length / 2
        val point = px(mid)
        val lpoints = px.slice(0, mid)
        val rpoints = px.slice(mid + 1, px.length)
        //println("pont " + point)
        //println("left " + lpoints)
        //println("right " + rpoints)
        val lminDistance = comparePoints(lpoints, minDistance)
        val rminDistance = comparePoints(rpoints, minDistance)

        var distance = getDistance(point, px.head)

        //println("left " + lpoints)
        //println("right " + rpoints)
        for (i <- 0 until lpoints.length; j <- 0 until rpoints.length) {
          val dist = getDistance(lpoints(i), rpoints(j))
          if (dist < distance)
            distance = dist
        }
        math.min(math.min(lminDistance, rminDistance), distance)
      }
    }

  val minDistance = comparePoints(points.sorted, Double.PositiveInfinity)
  BigDecimal(minDistance).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble
}


List((0, 0)) splitAt 0

val p1 = List((0, 0), (3, 4))
closest(p1)

val p2 = List((7, 7), (1, 100), (4, 8), (7, 7))
closest(p2)

val p3 = List((4, 4), (-2, -2), (-3, -4), (-1, 3), (2, 3), (-4, 0), (1, 1), (-1, -1), (3, -1), (-4, 2), (-2, 4))
closest(p3)

val p4 = List((7, 7), (1, 100), (7, 8))
closest(p4)