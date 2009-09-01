import org.specs._
import scala.io.Source

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      val step = Gitwatch.toJson(".git")
      step must_!= ""
    }
    "test" in {
      val p = Runtime.getRuntime.exec("git rev-list HEAD")
      val output = Source.fromInputStream(p.getInputStream).getLines.mkString
      println(output)
      output must_!= ""
    }
  }
}
