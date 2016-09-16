def sort(xs: Array[Int]): Array[Int] = {
  if (xs.length <= 1) xs
  else {
    val pivot = xs(xs.length / 2)
    Array.concat(
    sort(xs filter (pivot >)),
         xs filter (pivot ==),
         sort(xs filter (pivot <)))
  }
}

val a1 = Array(2, 3, 9, 2, 2)
sort(a1).toList