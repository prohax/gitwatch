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
        val parents = head.parents
        val result = if (parents.isEmpty) {
          map + ("INIT" -> List(head))
        } else {
          map + (parents.first -> (head :: map.get(head.parents.first).getOrElse(Nil)))
        }
        println("      --  result = " + result)
        result
      }
    }
  }

  def graph(cs: List[Node]): List[GraphedNode] = {
    cs.map(GraphedNode(_, 0))
  }
}