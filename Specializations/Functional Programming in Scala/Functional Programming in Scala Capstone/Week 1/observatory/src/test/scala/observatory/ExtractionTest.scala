package observatory

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ExtractionTest extends FunSuite  with BeforeAndAfterAll {
  def initializeExtraction(): Boolean =
    try {
      Extraction
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  override def afterAll(): Unit = {
    assert(initializeExtraction(), " -- did you fill in all the values in Extraction (conf, sc, wikiRdd)?")
    import Extraction._
    spark.stop()
  }

  test("'occurrencesOfLang' should work for (specific) RDD with one element") {
    assert(initializeExtraction(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import Extraction._
//    val rdd = sc.parallelize(Seq(WikipediaArticle("title", "Java Jakarta")))
//    val res = (occurrencesOfLang("Java", rdd) == 1)
//    assert(res, "occurrencesOfLang given (specific) RDD with one element should equal to 1")
  }
}