import collection.immutable.HashMap
import collection.mutable.ArrayBuffer

case class Node(id: String, parents: List[String], timestamp: Long)
case class GraphedNode(node: Node, y: Int)

class StatefulGrapher(commits: Map[String, Node], forwardMap: Map[String, List[Node]]) {
  val seen = new ArrayBuffer[Node]

  def graph(level: Int, current: Node): List[GraphedNode] = {
    if (seen.contains(current)) {
      Nil
    } else {
      seen += current
      //dirty mutation
      val branches = new ArrayBuffer[Node]()
      val merges = new ArrayBuffer[String]()
      val results = new ArrayBuffer[GraphedNode]()
      results append GraphedNode(current, level)
      forwardMap.getOrElse(current.id, Nil) match {
        case Nil => ()
        case head :: tail => branches appendAll tail
      }
      current.parents match {
        case Nil => ()
        case head :: tail => {
          results appendAll graph(level,commits(head))
          merges appendAll tail
        }
      }
      results appendAll merges.flatMap((c) => graph(level+1,commits(c)))
      results appendAll branches.flatMap(graph(level+1,_))
      results.toList
    }
  }
}

object Grapher {
  private def add(map: Map[String, List[Node]], head: Node, parent: String) = {
    map + (parent -> (head :: map.get(parent).getOrElse(Nil)))
  }

  def forwardGraph(cs: List[Node]): Map[String, List[Node]] = cs match {
    case Nil => {
      Map("INIT" -> Nil)
    }
    case head :: tail => {
      val map = forwardGraph(tail)
      head.parents match {
        case Nil => add(map, head, "INIT")
        case parents => parents.foldLeft(map)((m, s) => add(m, head, s))
      }
    }
  }

  def drawBack(node: Node)(implicit env: (Map[String, Node], Map[String, List[Node]])): List[GraphedNode] = {
    GraphedNode(node, 0) :: node.parents.take(1).flatMap((parent) => drawBack(env._1(parent)))
  }

  def graph(cs: List[Node], master: String): List[GraphedNode] = {
    val commits = cs.foldLeft(Map[String, Node]())((m, c) => m + (c.id -> c))
    val forwardMap = forwardGraph(cs)

    implicit val both = (commits, forwardMap)
    //    drawBack(commits(master))

    new StatefulGrapher(commits, forwardMap).graph(0, commits(master)).toList
  }
}