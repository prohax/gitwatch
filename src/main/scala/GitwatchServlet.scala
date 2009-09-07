import scalaz._
import scalaz.http.servlet.{HttpServletRequest}
import scalaz.http.response._
import scalaz.http.request._
import scalaz.http.scapps.Route._
import scalaz.http.scapps.Scapps._
import scalaz.http.scapps.{BaseApp, Route}
import Web._ 

final class GitwatchServlet extends BaseApp {
  val routes: Kleisli[Option, Request[Stream], Response[Stream]] =
      List(
        exactPath("/") >=> GET >=> webRoot _
/*        startsWith("/api") >=> List(
          exactPath("/") >=> GET >=> apiUsage _,
          startsWith("/register") >=> POST >=> apiRegister _,
          startsWith("/registrants") >=> GET >=> apiRegistrants _,
          startsWith("/search") >=> GET >=> apiSearch _
        )
*/    )

  def route(implicit request: Request[Stream], servletRequest: HttpServletRequest) = routes(request)
}

/*  get("/:repo/initial") {
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
*/