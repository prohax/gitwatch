import scala.xml.Elem
import scalaz._
import scalaz.http.response._
import scalaz.http.request._
import scalaz.http.scapps.Scapps._
import scalaz.http.scapps.ViewHelpers.Html._
import View._, ViewHelpers._

object Web {
  def webRoot(request: Request[Stream]): Option[Response[Stream]] = {
    implicit val r = request
    implicit val header: Option[Elem] = None
    val body =
        <div>
          <h2>Gitwatch.</h2>
        </div>

    Some(htmlResponse(OK, html(title("Gitwatch"), body)))
  }
}
