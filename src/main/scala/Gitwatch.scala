import java.io.{IOException, File}
import org.spearce.jgit.lib.Repository
import collection.jcl.Conversions._

case class JsonCommit(id: String, )

/**
 * TODO: Class comment.
 */
object Gitwatch {
  def toJson(gitDir: String): String = {
    try {
      val g = new File(gitDir)
      val repo = new Repository(g)
      val refs = repo.getAllRefs

      val head = repo.mapCommit("HEAD")
      println(head)
      refs.toString
    } catch {
      case e => e.toString
    }
  }
}