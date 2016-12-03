trait Table {
  def getParent: Table
}

case class Node(numberOfRows: Int, rank: Int, parent: Table) extends Table {
  override def getParent: Table = parent
}

case class Root(numberOfRows: Int, rank: Int) extends Table{
  override def getParent: Table = this
}

def findRoot(table: Table): Table =
  if (table != table.getParent()) findRoot(table.getParent())
  else table

def mergeTable(source: Table, destination: Table): Table = {

}