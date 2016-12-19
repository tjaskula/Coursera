class Table(var numberOfRows: Int) {
  var parent: Table = this
  var rank: Int = 0

  def getParent: Table = {
    var superRoot: Table = this
    var i: Table = this
    while (superRoot != superRoot.parent) {
      superRoot = superRoot.parent
    }
    while (i != superRoot) {
      val oldParent: Table = i.parent
      i.parent = superRoot
      i = oldParent
    }
    superRoot
  }

  override def toString: String = "Table(" + numberOfRows + ", " + rank + ")"
}

var maximumNumberOfRows = -1

def mergeTable(destination: Table, source: Table): Unit = {
  val realSource = source.getParent
  val realDestination = destination.getParent
  if (realDestination != realSource) {
    if (realDestination.rank >= realSource.rank) {
      realSource.parent = realDestination
      realDestination.numberOfRows += realSource.numberOfRows
      realSource.numberOfRows = 0
    }
    else {
      realDestination.parent = realSource
      realSource.numberOfRows += realDestination.numberOfRows
      realDestination.numberOfRows = 0
    }
    if (realSource.rank == realDestination.rank)
      realDestination.rank += 1
    maximumNumberOfRows = math.max(
      math.max(maximumNumberOfRows, realSource.numberOfRows),
      realDestination.numberOfRows)
  }
  println("Max: " + maximumNumberOfRows)
  println("Source: " + realSource)
  println("Destination: " + realDestination)
}

val nbRows1 = List(1, 1, 1, 1, 1)

def initiateRoots(nbRows: List[Int], acc: Array[Table]) : Array[Table] = nbRows match {
  case List() => acc
  case x :: xs =>
    maximumNumberOfRows = math.max(maximumNumberOfRows, x)
    initiateRoots(xs, acc :+ new Table(x))
}

val r1 = initiateRoots(nbRows1, Array.empty[Table])

mergeTable(r1(2), r1(4))
mergeTable(r1(1), r1(3))
mergeTable(r1(0), r1(3))
mergeTable(r1(4), r1(3))
mergeTable(r1(4), r1(2))

val nbRows2 = List(10, 0, 5, 0, 3, 3)

val r2 = initiateRoots(nbRows2, Array.empty[Table])

mergeTable(r2(5), r2(5))
mergeTable(r2(5), r2(4))
mergeTable(r2(4), r2(3))
mergeTable(r2(3), r2(2))