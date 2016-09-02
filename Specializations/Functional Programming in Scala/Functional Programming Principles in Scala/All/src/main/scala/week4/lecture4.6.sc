trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr
case class Var(x: String) extends Expr

object exprs {
  def show(e: Expr): String = e match {
    case Number(x) => x.toString
    case Sum(l, r) => show(l) + " + " + show(r)
    case Prod(l, r) => l match {
      case Sum(l1, r1) => "(" + show(l1) + " + " + show(r1) + ") * " + show(r)
      case _ => show(l) + " * " + show(r)
    }
    case Var(x) => x
  }
}

exprs.show(Sum(Number(1), Number(44)))

exprs.show(Sum(Prod(Number(2), Var("x")), Var("y")))

exprs.show(Prod(Sum(Number(2), Var("x")), Var("y")))