import scala.xml.Elem
import scalaz.http.response._
import scalaz.http.request._
import View._, ViewHelpers._

object MainLogic {
  def baseHtml(repo: String)(request: Request[Stream]): Option[Response[Stream]] = {
    implicit val r = request
    implicit val header: Option[Elem] = None

    Some(htmlResponse(OK, <html lang='en' xml:lang='en' xmlns='http://www.w3.org/1999/xhtml'>
      <head>
        <meta content='text/html; charset=utf-8' http-equiv='Content-Type' />
        <title>{repo} &mdash; Gitwatch</title>

        <link href="/stylesheets/styles.css" media="screen" rel="stylesheet" type="text/css" />
        <script src="/javascripts/jquery.js" type="text/javascript"></script>
        <script src="/javascripts/raphael.js" type="text/javascript"></script>
        <script src="/javascripts/gitwatch.js" type="text/javascript"></script>
      </head>
      <body>
        {repo_body(repo)}
      </body>
    </html>))
  }

  def repo_body(repo: String) = {
    <div id="holder"></div>
    <div id="world">
      <div id="message"></div>
    </div>
  }
}
