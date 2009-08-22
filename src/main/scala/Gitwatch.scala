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
      val g = new File(gitDir)
      val repo = new Repository(g)
      val refs = repo.getAllRefs

      val head = repo.mapCommit("HEAD")
      val his: List[GitCommit] = history(repo)(head)
      val gitCommits = his.foldLeft(Map[String,GitCommit]())((m,c) => m + (c.getCommitId.name -> c))
      val graph = Grapher.graph(his.map((c) => Node(
        c.getCommitId.name,
        List.fromArray(c.getParentIds.toArray).map(_.name),
        c.getAuthor.getWhen.getTime / 1000)), head.getCommitId.name)
        println(repo.openObject(repo.resolve("HEAD")).getBytes)
      val mappedHistory = graph.map((g: GraphedNode) => {
	    Map("id" -> g.node.id,
            "parents" -> g.node.parents,
            "timestamp" -> g.node.timestamp,
            "y" -> g.y,
            "author" -> gitCommits(g.node.id).getAuthor.getName,
            "committer" -> gitCommits(g.node.id).getCommitter.getName,
            "message" -> gitCommits(g.node.id).getMessage.replaceAll("\n","\\n"),
            "size" -> repo.openObject(repo.resolve(g.node.id)).getBytes.length
        )
      })
/*      println(mappedHistory)*/
      val json : String = Map("data" -> mappedHistory).jsonString
      json
  }
}