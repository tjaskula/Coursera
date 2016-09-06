import patmat.Huffman._

val sampleTree = makeCodeTree(
  makeCodeTree(Leaf('x', 1), Leaf('e', 1)),
  Leaf('t', 2)
)

val chars = List('a', 'b', 'a')

chars.foldLeft(Map[Char, Int] () withDefaultValue 0) {(m, c) => m.updated(c, m(c) + 1)}.toList

chars.map(c => (c, 1)).groupBy(_._1).mapValues(_.map(_._2).sum).toList.sorted

decodedSecret