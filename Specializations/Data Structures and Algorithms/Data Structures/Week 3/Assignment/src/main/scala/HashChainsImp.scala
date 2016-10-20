import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.util.StringTokenizer

object HashChainsImp {
  class HashChains(val bucketCount: Int) {
    private val prime = 1000000007
    private val multiplier = 263
    val storage = Array.fill[List[String]](bucketCount)(Nil)

    private def delChains(s: String, chain: List[String], newChain: List[String]): List[String] =
      chain match {
        case Nil => newChain
        case x::xs if x == s => delChains(s, xs, newChain)
        case x::xs => delChains(s, xs, newChain ::: List(x))
      }

    def hashFunc(s: String): Int = {
      val hash = 0L
      ((s foldRight hash) ((char, hashed) => (hashed * multiplier + char) % prime) % bucketCount).toInt
    }

    def add(s: String): HashChains = {
      val i = hashFunc(s)
      if (find(s) == "no") storage(i) = s :: storage(i)
      this
    }

    def del(s: String): HashChains = {
      val i = hashFunc(s)
      if (find(s) == "yes") storage(i) = delChains(s, storage(i), Nil)
      this
    }

    def find(s: String): String = {
      val i = hashFunc(s)
      val chain = storage(i)
      def findUntil(rest: List[String]): String = rest match {
        case Nil => "no"
        case x::xs if x == s => "yes"
        case x::xs => findUntil(xs)
      }
      findUntil(chain)
    }

    def check(i: Int): String = {
      (storage(i) foldLeft "") ((a, b) => a + " " + b).trim
    }
  }

  object PhoneBookManager {

    def add(s: String)(contacts: HashChains): HashChains =
      contacts add s

    def del(s: String)(contacts: HashChains): HashChains =
      contacts del s

    def find(s: String)(contacts: HashChains): HashChains = {
      println(contacts.find(s))
      contacts
    }

    def check(i: Int)(contacts: HashChains): HashChains = {
      println(contacts.check(i))
      contacts
    }

    def process(n: Int, bucketCount: Int)(read: Int => String) = {
      val contacts = new HashChains(bucketCount)
      def processIter(i: Int, contacts: HashChains): Unit = {
        if (i == n) Unit
        else {
          val line = read(i).split("\\s+")
          val operation: (HashChains => HashChains) =
            if (line(0) == "add") add(line(1))
            else if (line(0) == "del") del(line(1))
            else if (line(0) == "find") find(line(1))
            else if (line(0) == "check") check(line(1).toInt)
            else c => contacts
          processIter(i + 1, operation(contacts))
        }
      }
      processIter(0, contacts)
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val bucketCount: Int = scanner.nextInt
    val n: Int = scanner.nextInt

    PhoneBookManager.process(n, bucketCount)(_ => scanner.br.readLine())
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
    def nextLong: Long = next.toLong
  }
}
