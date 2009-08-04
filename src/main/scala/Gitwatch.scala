import java.io.{IOException, File}
import collection.jcl.Conversions._
import org.spearce.jgit.lib.{Commit => GitCommit, Repository}
import scapps.JSON
import scapps.Id._

case class Commit(id: String,
                  parents: List[String],
                  timestamp: Int,
                  y: Int,
                  author: String,
                  committer: String,
                  message: String,
                  size: Float)

object Gitwatch {
  def history(repo: Repository)(c: GitCommit): List[GitCommit] =
    List.fromArray(c.getParentIds).map(repo.mapCommit(_)) match {
      case Nil => c :: Nil
      case parents => c :: parents.flatMap(history(repo)(_))
    }

  def toJson(gitDir: String): String = {
    try {
      val g = new File(gitDir)
      val repo = new Repository(g)
      val refs = repo.getAllRefs

      val head = repo.mapCommit("HEAD")
      val his: List[GitCommit] = history(repo)(head)
      val mappedHistory = his.map((c: GitCommit) => {
	    Map("id" -> c.getCommitId.name,
            "parents" -> List.fromArray(c.getParentIds.toArray).map(_.name),
            "timestamp" -> c.getAuthor.getWhen.getTime / 1000,
            "y" -> 0,
            "author" -> c.getAuthor.getName,
            "committer" -> c.getCommitter.getName,
            "message" -> c.getMessage,
            "size" -> 0.0
        )
      })
/*      println(mappedHistory)*/
      val json : String = Map("data" -> mappedHistory).jsonString
      json
    } catch {
      case e => e.toString
    }
  }
}