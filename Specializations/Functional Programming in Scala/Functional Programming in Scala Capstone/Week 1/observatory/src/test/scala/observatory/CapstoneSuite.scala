package observatory

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterAll
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CapstoneSuite
  extends ExtractionTest
    with VisualizationTest
    with InteractionTest
    with ManipulationTest
    with Visualization2Test
    with Interaction2Test with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    assert(initializeExtraction(), " -- did you fill in all the values in Extraction (conf, sc, rdd)?")
    import Extraction._
    spark.stop()
  }
}

