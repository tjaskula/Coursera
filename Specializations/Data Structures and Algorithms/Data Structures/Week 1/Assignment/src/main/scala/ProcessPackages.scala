import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.util.StringTokenizer

import scala.collection.mutable.Queue

object ProcessPackages {
  class Request(val arrivalTime: Int, val processTime: Int)
  class Response(val dropped: Boolean, val startTime: Int)

  class Buffer(val size: Int) {
    val buffer = Queue[Int]()

    def process(request: Request): Response = {
      def freeBuffer(q: Queue[Int]): Unit = {
        if (q.nonEmpty && q.head <= request.arrivalTime) {
          q.dequeue
          freeBuffer(q)
        }
      }

      freeBuffer(buffer)

      // If still full, just drop it
      if (buffer.length == size) new Response(true, -1)
      else {
        val startTime = if (buffer.isEmpty) request.arrivalTime else buffer.last
        buffer.enqueue(startTime + request.processTime)
        new Response(false, startTime)
      }
    }
  }

  object Process {
    def readQueries(requestCount: Int)(read: Int => (Int, Int)): List[Request] = {
      def fillRequests(i: Int, requests: List[Request]): List[Request] = {
        if (i >= requestCount) requests
        else {
          val rawRequest = read(i)
          val request = new Request(rawRequest._1, rawRequest._2)
          fillRequests(i + 1, request :: requests)
        }
      }
      fillRequests(0, Nil)
    }

    def processRequests(requests: List[Request], buffer: Buffer): List[Response] =
      requests map (req => buffer.process(req))

    def printResponses(responses: List[Response]): Unit =
      responses.foreach(res =>
        if (res.dropped) println(-1)
        else println(res.startTime))

    def run(bufferSize: Int, requestCount: Int)(read: Int => (Int, Int)): Unit = {
      val queries = readQueries(requestCount)(read)
      val responses = processRequests(queries.reverse, new Buffer(bufferSize))
      printResponses(responses)
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val size: Int = scanner.nextInt
    val count: Int = scanner.nextInt

    Process.run(size, count)(_ => (scanner.nextInt, scanner.nextInt))
  }

  class FastScanner(val stream: InputStream) {

    var br: BufferedReader = null
    var st: StringTokenizer = null

    try
      br = new BufferedReader(new InputStreamReader(stream))

    catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }

    def next: String = {
      while (st == null || !st.hasMoreTokens)
        try
          st = new StringTokenizer(br.readLine)

        catch {
          case e: IOException => {
            e.printStackTrace()
          }
        }
      st.nextToken
    }

    def nextInt: Int = next.toInt
  }
}
