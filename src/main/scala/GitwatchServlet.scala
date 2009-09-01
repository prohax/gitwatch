import javax.servlet._
import javax.servlet.http._

class GitwatchServlet extends HttpServlet {

  override def service(request: HttpServletRequest, response: HttpServletResponse) {
    response.getWriter println "o hai"
  }

}
