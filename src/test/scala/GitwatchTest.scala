import org.specs._

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      val step = Gitwatch.toJson(".git")
      step must_!= ""
    }
  }
}
