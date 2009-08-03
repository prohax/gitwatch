
/**
 * TODO: Class comment.
 */
import org.specs._

object helloWorld extends Specification {
  "hello world" should {
    "have 11 characters" in {
      "hello world".size must_== 11
    }
    "match 'h.* w.*'" in {
      "hello world" must be matching("h.* w.*")
    }
  }
}

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      Gitwatch.toJson("~/src/step/.git") must_!= ""
    }
  }
}