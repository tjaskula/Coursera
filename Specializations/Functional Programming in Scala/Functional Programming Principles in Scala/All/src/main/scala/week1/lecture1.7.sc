def factorial(x: Int) = {
  def factorialIter(x: Int, acc: Int) : Int =
    if (x == 0) acc
    else factorialIter(x - 1, x * acc)
  factorialIter(x, 1)
}

factorial(4)