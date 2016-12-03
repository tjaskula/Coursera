def loop: Boolean = loop

def and(x: Boolean, y: => Boolean) =
  if (x) y else false

def or(x: Boolean, y: => Boolean) =
  if (x) true else y

and(true, false)
and(false, true)
and(true, true)
and(false, false)
and(false, loop)
and(loop, true)

or(false, false)
or(true, false)
or(false, true)
or(true, true)
or(true, loop)