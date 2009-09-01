import org.specs._
import scala.io.Source

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      val json = Gitwatch.toJson(".git")
      val p = Runtime.getRuntime.exec("git rev-list HEAD")
      val output = Source.fromInputStream(p.getInputStream).getLines.toString
      json.length must beGreaterThan(output.length)
    }
  }
}
