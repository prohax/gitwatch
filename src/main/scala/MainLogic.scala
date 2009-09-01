
object MainLogic {
  def baseHtml(repo: String) = {
    <html lang='en' xml:lang='en' xmlns='http://www.w3.org/1999/xhtml'>
      <head>
        <meta content='text/html; charset=utf-8' http-equiv='Content-Type' />
        <title>{repo} &mdash; Gitwatch</title>

        <link href="styles.css" media="screen" rel="stylesheet" type="text/css" />
        <script src="jquery.js" type="text/javascript"></script>
        <script src="raphael.js" type="text/javascript"></script>
        <script src="test.js" type="text/javascript"></script>
      </head>
      <body>
        {repo_body(repo)}
      </body>
    </html>
  }

  def repo_body(repo: String) = {
    <div id="holder"></div>
    <div id="world">
      <div id="message"></div>
    </div>
  }
}