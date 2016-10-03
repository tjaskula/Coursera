import java.io._
import java.util.StringTokenizer

import scala.annotation.tailrec

object TreeHeightImp {

  class TreeDepth(val parents: Array[(Int, Int)], n: Int) {
    val depths = Array.fill[Int](n)(0)

    @tailrec
    private def getParentStack(idx: Int, parent: Int, stack: List[Int] = Nil): List[Int] = {
      if (parent != -1 && depths(idx) == 0) getParentStack(parent, parents(parent)._2, idx :: stack)
      else idx :: stack
    }

    @tailrec
    private def countDepth(stack: List[Int], maxDepth: Int): Int =
      stack match {
        case Nil => maxDepth
        case x::xs => {
          depths(x) = math.max(depths(x), maxDepth)
          countDepth(xs, depths(x) + 1)
        }
      }

    def maxDepth(): Int = {
      @tailrec
      def maxDepthIter(parents: Array[(Int, Int)], maxDepth: Int): Int =
        parents match {
          case Array() => maxDepth
          case _ => {
            val indx = parents.head._1
            val parent = parents.head._2
            val parentStack = getParentStack(indx, parent)
            val newDepth = depths(indx)
            val depth = countDepth(parentStack, newDepth)
            maxDepthIter(parents.tail, if (maxDepth < depth) depth else maxDepth)
          }
        }
      maxDepthIter(parents, 0)
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(new FileInputStream(new File("/Users/tjaskula/Documents/GitHub/Coursera/Specializations/Data Structures and Algorithms/Data Structures/Week 1/Starters PA1/tree_height/tests/16")))
    val t0 = System.nanoTime()
    val n: Int = scanner.nextInt

    val a = Array.ofDim[(Int, Int)](n)
    for (i <- 0 until n) {
      a(i) = (i, scanner.nextInt)
    }
    /*def buildStartsEnds(i: Int, l: List[(Int, Int)]): List[(Int, Int)] =
      if (i == n) l
      else buildStartsEnds(i + 1, l ::: List((i, scanner.nextInt)))*/

    //val tree = buildStartsEnds(0, Nil)
    val t0bis = System.nanoTime()
    println(new TreeDepth(a, n)maxDepth())
    val t1 = System.nanoTime()
    val elapsed = (t1 - t0).toDouble / 1000000000.0
    val elapsedAlgo = (t1 - t0bis).toDouble / 1000000000.0
    println("Elapsed time with loading: " + elapsed + "ns")
    println("Elapsed time w/o loading: " + elapsedAlgo + "ns")
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