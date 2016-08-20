class Rational(x: Int, y: Int) {
  require(y != 0, "denominator must be nonzero")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int) : Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def less(r: Rational) = numer * r.denom < r.numer * denom
  def max(r: Rational) = if (this.less(r)) r else this
  def neg: Rational = new Rational(-numer, denom)
  def add(r: Rational) =
    new Rational(numer * r.denom + r.numer * denom,
      denom * r.denom)
  def sub(r: Rational) = add(r.neg)
  def mul(r: Rational) =
    new Rational(numer * r.numer, denom * r.denom)

  override def toString = numer + "/" + denom
}

val x = new Rational(1, 2)
val y = new Rational(3, 4)
val z = new Rational(1, 4)

x.add(y).sub(z)
x.mul(y)