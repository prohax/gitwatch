import javax.servlet._
import javax.servlet.http._
import com.thinkminimo.step._

class GitwatchServlet extends Step {

  get("/:repo/initial") {
    contentType = "application/json"
    GitProHax.run("/Users/glen/src/" + params(":repo") + "/.git", "refs/heads/master")
  }

  get("/:repo/update") {
    "update"
  }

  get("/:repo/") {
    contentType = "text/html"
    MainLogic.baseHtml(params(":repo"))
  }

  get("/") {
    contentType = "text/html"
    <h1>listing repos:</h1>
  }
}
