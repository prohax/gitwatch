// TODO Move these to scalaz
// TODO Add JSON to ContentTypeResolver. Add these also.
// TODO Use javax.activation.mimteype
object MimeType {
  implicit def MimeTypeToString(m: MimeType): String = m.asString
}
sealed trait MimeType {
  val asString: String
}
case object JsonMimeType extends MimeType {
  override val asString = "application/json"
}
case object HtmlMimeType extends MimeType {
    override val asString = "text/html"
}
case object WwwFormUrlEncodedMimeType extends MimeType {
    override val asString = "application/x-www-form-urlencoded"
}

object View {
  import MimeType._
  import _root_.scapps.{JSON, Id}, JSON._, Id._
  import scala.xml._
  import scalaz.http.scapps.Scapps._
  import scalaz.http._, StreamStreamApplication._
  import scalaz.http.request.Request
  import scalaz.http.response._
  import scalaz.http.response.xhtml.Doctype._
  import scalaz.Scalaz._

  implicit val charSet = scalaz.CharSet.UTF8

  def jsonResponse[T](status: Status, t: T)(implicit request: Request[Stream], jsonValuer: JSON[T]) = status(ContentType, JsonMimeType) << t.jsonString

  def htmlResponse(status: Status, content: Elem)(implicit request: Request[Stream]) = status(ContentType, HtmlMimeType) << transitional << content

  def html[A](title: String, bodyContent: A)(implicit request: Request[Stream], additionalHeader: Option[Elem]): Elem =
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <title>{title}</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      {~(additionalHeader)}
    </head>
    <body>
      {bodyContent}
    </body>
    </html>
}

object ViewHelpers {
  def title(base: String): String = "Slinky Demo: " + base
}
