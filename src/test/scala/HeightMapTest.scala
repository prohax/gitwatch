import scala.Random
import org.specs._


object HeightMapTest extends Specification {
  "heightmap" should {
    "say no" in {
      val h = new HeightMap()
      h currentMax 0L must beEqualTo(0)
      h currentMax 10L must beEqualTo(0)
      h currentMax -5L must beEqualTo(0)
    }
    "say yes for a single" in {
      val h = new HeightMap()
      h.add(1, 0L, 10L)
      h currentMax 0L must beEqualTo(1)
      h currentMax 10L must beEqualTo(1)
      h currentMax -5L must beEqualTo(0)
    }
//    "say yes for two" in {
//      val h = new HeightMap()
//      h.add(1, 0L, 10L)
//      h.add(2, 5L, 12L)
//      h currentMax 0L must beEqualTo(1)
//      h currentMax 4L must beEqualTo(1)
//      h currentMax 5L must beEqualTo(2)
//      h currentMax 11L must beEqualTo(2)
//      h currentMax -5L must beEqualTo(0)
//    }
  }
}