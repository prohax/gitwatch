import collection.immutable.HashMap

case class Node(id: String, parents: List[String], timestamp: Int)
case class GraphedNode(node: Node, y: Int)


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

  def drawBack(node: Node)(implicit env: (Map[String,Node], Map[String, List[Node]])): List[GraphedNode] = {
    println("      --  node = " + node)
    GraphedNode(node, 0) :: node.parents.take(1).flatMap((parent) => drawBack(env._1(parent)))
  }

  def graph(cs: List[Node], master: String): List[GraphedNode] = {
    val commits = cs.foldLeft(Map[String, Node]())((m,c) => m + (c.id -> c))
    val forwardMap = forwardGraph(cs)
    implicit val both = (commits, forwardMap)

    drawBack(commits(master))
  }
}