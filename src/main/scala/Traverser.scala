import collection.mutable.{HashMap, Queue}
import org.spearce.jgit.lib.{Commit => GitCommit, Repository}
import collection.jcl.Conversions._

class Traverser(repo: Repository) {
  val seen = new HashMap[String, GitCommit]
  val queue = new Queue[GitCommit]

  def traverseAll(initialQueue: List[GitCommit]) = {
    queue ++= initialQueue
    while (!queue.isEmpty) {
      val c = queue.dequeue
      val id = c.getCommitId.name
      if (!(seen contains id)) {
        queue ++= c.getParentIds.map(repo.mapCommit(_))
        seen += (id -> c)
      }
    }
    seen.values.toList
  }
}

object Traverser {
  def history(repo: Repository, c: GitCommit): List[GitCommit] = {
    (new Traverser(repo)).traverseAll(List(c))
  }
}
