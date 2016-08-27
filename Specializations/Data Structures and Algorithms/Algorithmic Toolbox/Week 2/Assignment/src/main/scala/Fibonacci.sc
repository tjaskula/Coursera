def fibo(n: Int) : Long =
  if (n <= 1) n
  else fibo(n - 1) + fibo(n - 2)

fibo(10)
fibo(40)