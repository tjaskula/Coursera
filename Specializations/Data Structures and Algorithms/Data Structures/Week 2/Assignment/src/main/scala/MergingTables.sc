class Table(numberOfRows: Int) {
  val parent: Table = this
  val rank: Int = 0

  def getParent(): Table = parent
}

def findRoot(table: Table): Table =
  if (table != table.getParent()) findRoot(table.getParent())
  else table