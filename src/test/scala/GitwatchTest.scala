
/**
 * TODO: Class comment.
 */
import org.specs._

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      Gitwatch.toJson("~/src/step/.git") must_!= ""
//      Gitwatch.toJson("~/src/step/.git") must_!= "lol"
    }
  }
}
