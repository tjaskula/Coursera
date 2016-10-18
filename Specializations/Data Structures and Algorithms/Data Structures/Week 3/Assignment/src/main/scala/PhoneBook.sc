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

val indt1 = List[String]("add 911 police", "add 76213 Mom",
  "add 17239 Bob", "find 76213", "find 910", "find 911", "del 910",
  "del 911", "find 911", "find 76213", "add 76213 daddy", "find 76213")

PhoneBookManager.process(12)(i => indt1(i))