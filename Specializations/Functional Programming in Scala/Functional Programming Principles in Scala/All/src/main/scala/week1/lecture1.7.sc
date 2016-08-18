def factorial(x: Int) = {
  def factorialIter(x: Int, acc: Int) : Int =
    if (x == 0) acc
    else factorialIter(x - 1, x * acc)
  factorialIter(x, 1)
}

factorial(4)

def gcd(a: Int, b: Int) : Int =
  if (b == 0) a else gcd(b, a % b)

gcd(14, 21)