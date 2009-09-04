object Main {
  def main(args: Array[String]) {
    try {
      args match {
        case Array() => println("Usage: run GIT_DIR [OUTPUT_FILE]")
        case Array(git) => println(GitProHax.run(git,"refs/heads/master"))
        case Array(git, out) => {
          println("Writing " + git + " to " + out)
          println("i lied.")
        }
      }
    } catch {
      case e: Exception => println("FAILED: " + e.getMessage + "\n" + e.getStackTrace.mkString("\n"))
    }
  }
}