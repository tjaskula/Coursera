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

new HashChains(5).hashFunc("world")
new HashChains(5).hashFunc("HellO")

(0 * 263 + 100) % 1000000007
(100 * 263 + 108) % 1000000007
(26408 * 263 + 114) % 1000000007
(6945418 * 263 + 111) % 1000000007
(826645038L * 263 + 119) % 1000000007
407643594 % 5

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
          else check(line(1).toInt)
        processIter(i + 1, operation(contacts))
      }
    }
    processIter(0, contacts)
  }
}

val indt1 = List[String]("add world", "add HellO",
  "check 4", "find World", "find world", "del world", "check 4",
  "del HellO", "add luck", "add GooD", "check 2", "del good")

PhoneBookManager.process(12, 5)(i => indt1(i))

val indt2 = List[String]("add test", "add test",
  "find test", "del test", "find test", "find Test", "add Test",
  "find Test")

PhoneBookManager.process(8, 4)(i => indt2(i))

val indt3 = List[String]("check 0", "find help",
  "add help", "add del", "add add", "find add", "find del",
  "del del", "find del", "check 0", "check 1", "check 2")

PhoneBookManager.process(12, 3)(i => indt3(i))