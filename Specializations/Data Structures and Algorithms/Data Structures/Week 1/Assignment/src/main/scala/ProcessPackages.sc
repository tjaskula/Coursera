import scala.collection.mutable.Queue

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

Process.run(1, 0)(i => (-1, -1))

val p1 = List((0, 0))
Process.run(1, 1)(i => p1(i))

val p2 = List((0, 1), (0, 1))
Process.run(1, 2)(i => p2(i))

val p3 = List((0, 1), (1, 1))
Process.run(1, 2)(i => p3(i))

val p4 = List((0, 10), (0, 3), (6, 2), (7, 1), (7, 1))
Process.run(3, 5)(i => p4(i))