package barneshut
package conctrees

import scala.reflect.ClassTag
import org.scalameter._

class ConcBuffer[@specialized(Byte, Char, Int, Long, Float, Double) T: ClassTag](
  val k: Int, private var conc: Conc[T]
) extends Traversable[T] {
  require(k > 0)

  def this() = this(128, Conc.Empty)
  
  private var chunk: Array[T] = new Array(k)
  private var lastSize: Int = 0

  def foreach[U](f: T => U): Unit = {
    conc.foreach(f)

    var i = 0
    while (i < lastSize) {
      f(chunk(i))
      i += 1
    }
  }

  final def +=(elem: T): this.type = {
    if (lastSize >= k) expand()
    chunk(lastSize) = elem
    lastSize += 1
    this
  }

  final def combine(that: ConcBuffer[T]): ConcBuffer[T] = {
    val combinedConc = this.result <> that.result
    this.clear()
    that.clear()
    new ConcBuffer(k, combinedConc)
  }

  private def pack() {
    conc = Conc.appendTop(conc, new Conc.Chunk(chunk, lastSize, k))
  }

  private def expand() {
    pack()
    chunk = new Array(k)
    lastSize = 0
  }

  def clear() {
    conc = Conc.Empty
    chunk = new Array(k)
    lastSize = 0
  }

  def result: Conc[T] = {
    pack()
    conc
  }
}

object ConcBufferRunner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]) {
    val size = 1000000

    def run(p: Int) {
      val taskSupport = new collection.parallel.ForkJoinTaskSupport(
        new scala.concurrent.forkjoin.ForkJoinPool(p))
      val strings = (0 until size).map(_.toString)
      val time = standardConfig measure {
        val parallelized = strings.par
        parallelized.tasksupport = taskSupport
        parallelized.aggregate(new ConcBuffer[String])(_ += _, _ combine _).result
      }
      println(s"p = $p, time = $time ms")
    }

    run(1)
    run(2)
    run(4)
    run(8)
  }

}
