import sbt._

class GitwatchProject(info: ProjectInfo) extends DefaultWebProject(info) {
  import BasicScalaProject._

  override def jettyPort = 1337
  override def scanDirectories = ( temporaryWarPath / "WEB-INF" / "classes" ).get.toSeq
/*  override def useMavenConfigurations = true*/
/*  override def packageAction = packageTask(mainClasses +++ mainResources, outputPath, defaultJarName, packageOptions).dependsOn(compile) describedAs PackageDescription*/
/*  override def scanDirectories = ( temporaryWarPath / "WEB-INF" / ("classes" | "lib") ).get.map(_.asFile).toSeq*/

  val jetty2 = "org.mortbay.jetty" % "jetty" % "7.0.0pre3" % "test->default"
  val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided->default"

/*  val jettytester = "org.mortbay.jetty" % "jetty-servlet-tester" % "7.0.0pre3" % "test->default"*/
/*  val scalatest = "org.scala-tools.testing" % "scalatest" % "0.9.5" % "test->default"*/
}
