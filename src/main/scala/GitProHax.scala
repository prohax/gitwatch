import collection.immutable.TreeMap
import collection.mutable.{ArrayBuffer, HashSet, Queue, HashMap}
import java.io.File
import org.spearce.jgit.lib.{Commit => GitCommit, Repository}
import collection.jcl.{Map => JclMap}

case class GraphedCommit(c: GitCommit, y: Int)

class GitProHax(repo: Repository) {
  val seen = new HashMap[String, GraphedCommit]
  val heightMap = new HeightMap
  //needs to be a priority queue
  val mergesQueue = new Queue[(String, Int)]

  def graph(head: GitCommit, branches: List[GitCommit]): List[GraphedCommit] = {
    renderBack(head, 0)
    println(heightMap)
    mergesQueue.foreach(t => renderBack(repo.mapCommit(t._1), t._2))
    println(heightMap)

    val branchesByBranchTime = branches.map(b => (timeOf(findSeenParent(b)), b)).sort((a,b) => a._1 > b._1)
//    branchesByBranchTime.foreach(x => {
//
//    })

    seen.values.toList.sort((a,b) => timeOf(a.c) < timeOf(b.c))
  }

  def renderBack(c: GitCommit, y: Int) {
    val id = c.getCommitId.name
    if (!(seen contains id)) {
      parents(c) match {
        case Nil => ()
        case head :: tail => {
          mergesQueue ++= tail.map((_, y + 1))
          seen += (id -> GraphedCommit(c, y))
          val parent = repo.mapCommit(head)
          heightMap.add(y, timeOf(parent), timeOf(c))
          renderBack(parent, y)
        }
      }
    }
  }

  def findSeenParent(c: GitCommit) : GitCommit = parents(c) match {
    case Nil => c
    case head :: tail => findSeenParent(repo.mapCommit(head))
  }

  private def timeOf(c: GitCommit) = c.getCommitter.getWhen.getTime / 1000
  private def parents(c: GitCommit) : List[String] = List.fromArray(c.getParentIds.toArray).map(_.name)
}

object GitProHax {
  def run(gitDir: String, master: String) = {
    val repo = new Repository(new File(gitDir))
    val branches = JclMap(repo.getAllRefs)
    val head = repo.mapCommit(branches(master).getObjectId)
    val branchHeads = branches.values.map(x => repo.mapCommit(x.getObjectId)).toList
    val graphedCommits = new GitProHax(repo).graph(head, branchHeads)
    graphedCommits.map(g => (g.c.getCommitId.name.substring(0,7), g.y)).mkString("\n")
  }
}
