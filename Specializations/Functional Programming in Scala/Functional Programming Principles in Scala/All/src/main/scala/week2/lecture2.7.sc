class Rational(x: Int, y: Int) {
  require(y != 0, "denominator must be nonzero")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int) : Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def < (r: Rational) = numer * r.denom < r.numer * denom
  def max(r: Rational) = if (this < r) r else this
  def unary_- : Rational = new Rational(-numer, denom)
  def + (r: Rational) =
    new Rational(numer * r.denom + r.numer * denom,
      denom * r.denom)
  def - (r: Rational) = this + -r
  def * (r: Rational) =
    new Rational(numer * r.numer, denom * r.denom)

  override def toString = numer + "/" + denom
}

val x = new Rational(1, 2)
val y = new Rational(3, 4)
val z = new Rational(1, 4)

x + y - z
x * y