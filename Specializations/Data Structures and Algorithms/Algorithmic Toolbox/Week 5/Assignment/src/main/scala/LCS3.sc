def lcs(a: Array[Char], b: Array[Char]): String = {
  val solution = Array.fill(a.length + 1, b.length + 1)("")

  for (i <- 1 to a.length; j <- 1 to b.length) {
    if (a(i - 1) == b(j - 1)) solution(i)(j) = solution(i - 1)(j - 1) + " " + a(i - 1)
    else if (solution(i)(j - 1).length > solution(i - 1)(j).length) solution(i)(j) = solution(i)(j - 1)
    else solution(i)(j) = solution(i - 1)(j)
  }

  //printArray(solution)

  solution(a.length)(b.length)
}

def lcsCount(a: Array[Char], b: Array[Char]): Int = {
  val solution = Array.fill(a.length + 1, b.length + 1)(0)

  for (i <- 1 to a.length; j <- 1 to b.length) {
    if (a(i - 1) == b(j - 1)) solution(i)(j) = solution(i - 1)(j - 1) + 1
    else solution(i)(j) = Math.max(solution(i)(j - 1), solution(i - 1)(j))
  }

  //printArray(solution)

  solution(a.length)(b.length)
}

def lcs3(a: Array[Char], b: Array[Char], c: Array[Char]): String = {
  val solution = Array.fill(a.length + 1, b.length + 1, c.length + 1)("")

  for (i <- 1 to a.length; j <- 1 to b.length; k <- 1 to c.length) {
    if (a(i - 1) == b(j - 1) && b(j - 1) == c(k - 1)) solution(i)(j)(k) = solution(i - 1)(j - 1)(k - 1) + " " + a(i - 1)
    else if ((solution(i)(j - 1)(k).length > solution(i - 1)(j)(k).length) || (solution(i)(j)(k - 1).length > solution(i)(j - 1)(k).length))
      solution(i)(j)(k) =  solution(i)(j)(k - 1)
    else solution(i)(j)(k) = solution(i - 1)(j)(k)
  }

  //printArray(solution)

  solution(a.length)(b.length)(c.length)
}

def lcs3Count(a: Array[Int], b: Array[Int], c: Array[Int]): Int = {
  val solution = Array.fill(a.length + 1, b.length + 1, c.length + 1)(0)

  for (i <- 1 to a.length; j <- 1 to b.length; k <- 1 to c.length) {
    if (a(i - 1) == b(j - 1) && a(i - 1) == c(k - 1)) solution(i)(j)(k) = solution(i - 1)(j - 1)(k - 1) + 1
    else solution(i)(j)(k) = Math.max(solution(i - 1)(j)(k), Math.max(solution(i)(j - 1)(k), solution(i)(j)(k - 1)))
  }

  //printArray(solution)

  solution(a.length)(b.length)(c.length)
}

def printArray[T](a: Array[Array[T]]) = {
  println("--------------------")
  for (row <- a.indices; col <- a(row).indices) {
    if (col >= a(row).length - 1) println(a(row)(col))
    else print(a(row)(col) + " | ")
  }
  println("--------------------")
}

/*lcs3("12".toCharArray, "23".toCharArray, "34".toCharArray)
lcs3Count("12".toCharArray, "23".toCharArray, "34".toCharArray)

lcs3("1389".toCharArray, "1435".toCharArray, "4589".toCharArray)
lcs3Count("1389".toCharArray, "1435".toCharArray, "4589".toCharArray)

lcs3("123".toCharArray, "213".toCharArray, "135".toCharArray)
lcs3Count("123".toCharArray, "213".toCharArray, "135".toCharArray)*/

//lcs3("83217".toCharArray, "8 2 1 3 8 10 7".split(' ').map(_.head), "683147".toCharArray)
lcs3Count("8 3 2 1 7".split(' ').map(_.toInt), "8 2 1 3 8 10 7".split(' ').map(_.toInt), "6 8 3 1 4 7".split(' ').map(_.toInt))