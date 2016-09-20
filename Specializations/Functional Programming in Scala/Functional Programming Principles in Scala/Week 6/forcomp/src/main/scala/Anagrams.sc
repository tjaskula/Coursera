import forcomp._

Anagrams.dictionary map {case w => (Anagrams.wordOccurrences(w), w)} groupBy(t => t._2) flatten