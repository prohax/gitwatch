import org.spearce.jgit.lib.{Commit => GitCommit, Repository}
import collection.jcl.Conversions._

class Traverser {

}

object Traverser {
  def history(repo: Repository, c: GitCommit): List[GitCommit] = {
    c :: List.fromArray(c.getParentIds.map(repo.mapCommit(_)).flatMap(history(repo, _)))
  }
}
