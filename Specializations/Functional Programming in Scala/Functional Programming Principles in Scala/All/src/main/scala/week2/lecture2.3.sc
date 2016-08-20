import math.abs

val tolerance = 0.0001

def isCloseEnough(x: Double, y: Double) =
  abs((x - y) / x) / x < tolerance

def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  def iterate(guess: Double) : Double = {
    val next = f(guess)
    if (isCloseEnough(guess, next)) next
    else iterate(next)
  }
  iterate(firstGuess)
}

fixedPoint(x => 1 + x/2)(1)

def averageDump(f: Double => Double)(x: Double) = (x + f(x)) / 2

def sqrt(x: Double) =
  fixedPoint(averageDump(y => x / y))(x)

sqrt(2)