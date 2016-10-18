import java.io.{BufferedReader, IOException, InputStream, InputStreamReader}
import java.util.StringTokenizer

object PhoneBook {
  object PhoneBookManager {

    def add(number: String, name: String)(contacts: Map[String, String]): Map[String, String] =
      contacts + (number -> name)

    def del(number: String)(contacts: Map[String, String]): Map[String, String] =
      contacts - number

    def find(number: String)(contacts: Map[String, String]): Map[String, String] = {
      val result =
        if (contacts.contains(number)) contacts(number)
        else "not found"
      println(result)
      contacts
    }

    def process(n: Int)(read: Int => String) = {
      val contacts = Map[String, String]()
      def processIter(i: Int, contacts: Map[String, String]): Unit = {
        if (i == n) Unit
        else {
          val line = read(i).split(' ')
          val operation: (Map[String, String] => Map[String, String]) =
            if (line(0) == "add") add(line(1), line(2))
            else if (line(0) == "del") del(line(1))
            else find(line(1))
          processIter(i + 1, operation(contacts))
        }
      }
      processIter(0, contacts)
    }
  }

  def main(args: Array[String]): Unit = {
    val scanner: FastScanner = new FastScanner(System.in)
    val n: Int = scanner.nextInt

    val a = Array.ofDim[Int](n)

    PhoneBookManager.process(n)(_ => scanner.br.readLine())
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