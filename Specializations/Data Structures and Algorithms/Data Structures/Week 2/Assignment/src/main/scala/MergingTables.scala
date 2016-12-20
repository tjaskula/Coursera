import java.io._
import java.util.{Arrays, Locale, StringTokenizer}

object MergingTables {

  class InputReader(val stream: InputStream) {
    val reader = new BufferedReader(new InputStreamReader(stream), 32768)
    var tokenizer: StringTokenizer = null

    def next: String = {
      while (tokenizer == null || !tokenizer.hasMoreTokens) try
        tokenizer = new StringTokenizer(reader.readLine)

      catch {
        case e: IOException => {
          throw new RuntimeException(e)
        }
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
      writer.print(String.format(Locale.ENGLISH, format, args))
    }
  }
}