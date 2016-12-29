import java.io._
import java.util.{Locale, StringTokenizer}

object MergingTables {

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

  var maximumNumberOfRows: Int = -1

  def merge(destination: Table, source: Table): Unit = {
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
  }

  def main(args: Array[String]): Unit = {
    val reader = new InputReader(System.in)
    val writer = new OutputWriter(System.out)
    run(reader, writer)
    writer.writer.flush()
  }

  def run(reader: InputReader, writer: OutputWriter): Unit = {
    val n = reader.nextInt
    val m = reader.nextInt
    val tables = Array.ofDim[Table](n)
    for (i <- 0 until n) {
      val numberOfRows = reader.nextInt
      tables(i) = new Table(numberOfRows)
      // Maintain the global max during initialization
      maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows)
    }
    for (i <- 0 until m) {
      val destination = reader.nextInt - 1
      val source = reader.nextInt - 1
      merge(tables(destination), tables(source))
      writer.printf("%d\n", maximumNumberOfRows)
    }
  }

  class InputReader(val stream: InputStream) {
    val reader = new BufferedReader(new InputStreamReader(stream), 32768)
    var tokenizer: StringTokenizer = _

    def next: String = {
      while (tokenizer == null || !tokenizer.hasMoreTokens) try
        tokenizer = new StringTokenizer(reader.readLine)

      catch {
        case e: IOException =>
          throw new RuntimeException(e)
      }
      tokenizer.nextToken
    }

    def nextInt: Int = next.toInt
    def nextDouble: Double = next.toDouble
    def nextLong: Long = next.toLong
  }

  class OutputWriter(val stream: OutputStream) {
    val writer = new PrintWriter(stream)

    def printf(format: String, args: Any*) {
      writer.println(args.mkString)
    }
  }
}