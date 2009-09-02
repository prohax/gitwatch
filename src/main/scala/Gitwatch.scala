import java.io.{IOException, File}
import collection.jcl.Conversions._
import org.spearce.jgit.lib.{Commit => GitCommit, Repository}
import scapps.JSON
import scapps.Id._

object Gitwatch {
  private def graphedHistory(repo: Repository, head: GitCommit, hist: List[GitCommit]) = {
    Grapher.graph(hist.map((c) => Node(
      c.getCommitId.name,
      List.fromArray(c.getParentIds.toArray).map(_.name),
      c.getAuthor.getWhen.getTime / 1000)), head.getCommitId.name
    )
  }

  private def mappedGraph(repo: Repository, head: GitCommit) = {
    val hist = Traverser.history(repo, head)
    val commits = hist.foldLeft(Map[String, GitCommit]())((m, c) => m + (c.getCommitId.name -> c))

    graphedHistory(repo, head, hist).map((g: GraphedNode) => {
      Map(
        "id" -> g.node.id,
        "parents" -> g.node.parents,
        "timestamp" -> g.node.timestamp,
        "y" -> g.y,
        "author" -> commits(g.node.id).getAuthor.getName,
        "committer" -> commits(g.node.id).getCommitter.getName,
        "message" -> commits(g.node.id).getMessage.replaceAll("\n", "\\n"),
        "size" -> repo.openObject(repo.resolve(g.node.id)).getBytes.length
      )
    })
  }

  def toJson(gitDir: String): String = {
    val repo = new Repository(new File(gitDir))
    val head = repo.mapCommit("HEAD")

    Map("data" -> (
      if (head == null) "" else mappedGraph(repo, head)
    )).jsonString
  }
}
