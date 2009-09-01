import javax.servlet._
import javax.servlet.http._

import com.thinkminimo.step._

/*class GitwatchServlet extends HttpServlet {

  override def service(request: HttpServletRequest, response: HttpServletResponse) {
    response.getWriter println "o hai"
  }

}
*/

class GitwatchServlet extends Step {

  before {
    contentType = "text/html"
  }

  get("/:repo/initial.json") {
    "initial"
  }

  get("/:repo/update.json") {
    "update"
  }

  get("/:repo") {
    MainLogic.baseHtml(params(":repo"))
  }

  get("/") {
    <h1>listing repos:</h1>
  }
}
