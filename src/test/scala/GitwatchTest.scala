import org.specs._

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      val step = Gitwatch.toJson("/Users/glen/src/gitwatch/.git")
//      println(step)
      step must_!= ""
    }
  }
}
