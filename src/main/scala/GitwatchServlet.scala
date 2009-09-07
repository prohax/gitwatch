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
    (exactPath("/") >=> GET >=> webRoot _) ::
/*    (startsWith("/g") >=> GET >=> MainLogic.baseHtml("gitwatch") _) ::*/
    List("gitwatch", "step", "babushka").map(repo => {
      startsWith("/" + repo) >=> List(
        exactPath("/initial.json") >=> GET >=> GitProHax.toJson(repo, "refs/heads/master") _,
        exactPath("/") >=> GET >=> MainLogic.baseHtml(repo) _
      )
    })

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