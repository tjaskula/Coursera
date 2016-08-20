class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y

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