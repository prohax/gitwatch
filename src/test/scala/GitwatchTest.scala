import org.specs._

object GitwatchTest extends Specification {
  "gitwatch main" should {
    "list the branches of a simple project" in {
      val step = Gitwatch.toJson(".git")
      step must_!= ""
    }
    "test" in {
      val p = Runtime.getRuntime.exec("git rev-list HEAD")
      val in = p.getInputStream
      var ch = in.read
      val sb = new StringBuffer(512)
      while (ch != -1) {
        sb.append(ch.asInstanceOf[Char])
        ch = in.read
      }
      println(sb.toString)
      sb.toString must_!= ""
    }
  }
}
