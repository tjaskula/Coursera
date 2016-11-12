def merge(a: Array[Int], aux: Array[Int], lo: Int, mid: Int, hi: Int): Long = {
  def mergeBack(a: Array[Int], aux: Array[Int], k: Int, i: Int, j: Int, inversions: Long): Long = {
    if (k > hi) inversions
    else {
      val (newInversions, newI, newJ) =
          if (i > mid) {
            a(k) = aux(j)
            (inversions, i, j + 1)
          }
          else if (j > hi) {
            a(k) = aux(i)
            (inversions, i + 1, j)
          }
          else if (aux(j) < aux(i)) {
            a(k) = aux(j)
            (inversions + (mid - i + 1), i, j + 1)
          }
          else {
            a(k) = aux(i)
            (inversions, i + 1, j)
          }
      mergeBack(a, aux, k + 1, newI, newJ, newInversions)
    }
  }

  Array.copy(a, lo, aux, lo, hi - lo + 1)
  mergeBack(a, aux, lo, lo, mid + 1, 0)
}

def countMerge(a: Array[Int], b: Array[Int], aux: Array[Int], lo: Int, hi: Int, inversions: Long): Long = {
  if (hi <= lo) 0
  else {
    val mid = lo + (hi - lo) / 2
    val inversionsLeft = countMerge(a, b, aux, lo, mid, 0)
    val inversionsRight = countMerge(a, b, aux, mid + 1, hi, 0)
    val inversionsMerge = merge(b, aux, lo, mid, hi)
    inversionsLeft + inversionsRight + inversionsMerge
  }
}

def count(a: Array[Int]): Long = {
  val b = Array.ofDim[Int](a.length)
  val aux = Array.ofDim[Int](a.length)
  Array.copy(a, 0, b, 0, a.length)
  countMerge(a, b, aux, 0, a.length - 1, 0)
}

val l1 = Array(2, 3, 9, 2, 9)
count(l1)

val l2 = Array(2, 1, 1, 3, 4, 9, 5)
count(l2)

val l3 = Array(9, 8, 7, 3, 2, 1)
count(l3)

val l4 = Array(9, 9, 8, 8, 7, 7, 3, 3, 2, 2, 1, 1)
count(l4)

val l5 = Array(8, 15, 3, 1)
count(l5)

val l6 = Array(1, 5, 4, 8, 10, 2, 6, 9, 3, 7)
count(l6)

val l7 = Array(1, 5, 4, 8, 10, 2, 6, 9, 12, 11, 3, 7)
count(l7)