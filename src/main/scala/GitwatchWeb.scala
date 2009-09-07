import scala.xml.Elem
import scalaz._
import scalaz.http.response._
import scalaz.http.request._
import scalaz.http.scapps.Scapps._
import scalaz.http.scapps.ViewHelpers.Html._
import View._, ViewHelpers._

object Web {
  def lol(request: Request[Stream]): Option[Response[Stream]] = {
    implicit val r = request
    implicit val header: Option[Elem] = None
    Some(htmlResponse(OK, html(title("LOL"), <p>lol.</p>)))
  }
  def webRoot(request: Request[Stream]): Option[Response[Stream]] = {
    implicit val r = request
    implicit val header: Option[Elem] = None
    val body =
        <div>
          <h2>Gitwatch 2.</h2>
        </div>

    Some(htmlResponse(OK, html(title("Gitwatch"), body)))
  }
}
