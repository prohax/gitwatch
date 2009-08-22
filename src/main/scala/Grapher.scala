import collection.immutable.HashMap

case class Node(id: String, parents: List[String], timestamp: Int)
case class GraphedNode(node: Node, y: Int)


object Grapher {
  def recursiveGrouper(cs: List[Node]): Map[String, List[Node]] = {
    println("      --  cs = " + cs)
    cs match {
      case Nil => {
        println("      -- hello")
        Map("INIT" -> Nil)
      }
      case head :: tail => {
        println("      --  head = " + head)
        val map = recursiveGrouper(tail)
        if (head.parents.isEmpty) {
          map + ("INIT" -> List(head))
        } else {
          map + (head.parents.first -> List(head))
        }
      }
    }
  }

  def graph(cs: List[Node]): List[GraphedNode] = {
    cs.map(GraphedNode(_, 0))
  }
}