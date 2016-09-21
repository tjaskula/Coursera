import forcomp.Anagrams
import forcomp.Anagrams.{Occurrences, Word}

//def dico: Map[Occurrences, List[Word] =
//Anagrams.dictionary.groupBy((g) => Anagrams.wordOccurrences(g)) withDefaultValue List()

val sent = List("Roberto", "Carlos")

(for (word <- sent; occurence <- Anagrams.wordOccurrences(word)) yield occurence) groupBy(_._1)
Anagrams.wordOccurrences(sent mkString "")

val sentence = List("Linux", "rulez")
Anagrams.sentenceAnagrams(sentence)