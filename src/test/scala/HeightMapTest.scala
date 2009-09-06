import scala.Random
import org.specs._


object HeightMapTest extends Specification {
  "heightmap" should {
    "say no" in {
      val h = new HeightMap()
      h heightAt 0L must beEqualTo(0)
      h heightAt 10L must beEqualTo(0)
      h heightAt -5L must beEqualTo(0)
    }
    "say yes for a single" in {
      val h = new HeightMap()
      h.add(1, 0L, 10L)
      h heightAt 0L must beEqualTo(1)
      h heightAt 10L must beEqualTo(1)
      h heightAt -5L must beEqualTo(0)
    }
    "say yes for two" in {
      val h = new HeightMap()
      h.add(1, 0L, 10L)
      h.add(2, 5L, 12L)
      h heightAt 0L must beEqualTo(1)
      h heightAt 4L must beEqualTo(1)
      h heightAt 5L must beEqualTo(2)
      h heightAt 11L must beEqualTo(2)
      h heightAt -5L must beEqualTo(0)
    }
    "should work for ranges" in {
      val h = new HeightMap()
      h.add(1, 0L, 10L)
      h.add(2, 5L, 12L)
      h heightWithin (-5 until -2) must beEqualTo(0)
      h heightWithin (-5 until 2) must beEqualTo(1)
      h heightWithin (2 until 4) must beEqualTo(1)
      h heightWithin (2 until 7) must beEqualTo(2)
      h heightWithin (-10 until 20) must beEqualTo(2)
    }
  }
  "overlap" should {
    "work for equal ranges" in {
      HeightMap.overlap(1 until 10, 1 until 10) must beTrue
      HeightMap.overlap(3 until 4, 3 until 4) must beTrue
    }
    "work for distinct ranges" in {
      HeightMap.overlap(1 until 4, 5 until 10) must beFalse
      HeightMap.overlap(10 until 11, 5 until 9) must beFalse
    }
    "work for overlaps" in {
      HeightMap.overlap(3 until 8, 7 until 9) must beTrue
      HeightMap.overlap(2 until 4, 1 until 3) must beTrue
      HeightMap.overlap(1 until 4, 4 until 10) must beTrue
    }
    "work for inclusions" in {
      HeightMap.overlap(3 until 4, 1 until 10) must beTrue
      HeightMap.overlap(3 until 8, 5 until 7) must beTrue
    }
  }
}