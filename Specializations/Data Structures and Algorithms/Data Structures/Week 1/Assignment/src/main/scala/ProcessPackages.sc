import scala.collection.immutable.Queue

class Request(val arrivalTime: Int, val processTime: Int)
class Response(val dropped: Boolean, val startTime: Int)

class Buffer(val size: Int) {
  val buffer = Queue[Int]()

  def process(request: Request): Response = {
    new Response(false, -1)
  }
}

object Process {
  def readQueries(requestCount: Int)(read: Int => (Int, Int)): List[Request] = {
    def fillRequests(i: Int, requests: List[Request]): List[Request] = {
      if (i >= requestCount) requests
      else {
        val request = new Request(read(i)._1, read(i)._2)
        fillRequests(i + 1, requests ::: List(request))
      }
    }
    fillRequests(0, Nil)
  }

  def processRequests(requests: List[Request], buffer: Buffer): List[Response] =
    requests map (req => buffer.process(req))

  def printResponses(responses: List[Response]): Unit =
    responses.foreach(res =>
      if (res.dropped) println(-1)
      else println(res.startTime)
    )
}

//run(0, 1)(i => (-1, -1), i => println(i))

//val p1 = List((0, 0))
//run(1, 1)(i => p1(i), i => println(i))
//
//val p2 = List((0, 1), (0, 1))
//run(2, 1)(i => p2(i), i => println(i))
//
//val p3 = List((0, 1), (1, 1))
//run(2, 1)(i => p3(i), i => println(i))
//
//val p4 = List((0, 10), (0, 3), (6, 2), (7, 1), (7, 1))
//run(5, 3)(i => p4(i), i => println(i))