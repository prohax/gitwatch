import collection.immutable.HashMap

case class Node(id: String, parents: List[String], timestamp: Int)
case class GraphedNode(node: Node, y: Int)


object Grapher {
  def recursiveGrouper(cs: List[Node]): Map[String, List[Node]] = cs match {
    case Nil => Map("INIT" -> Nil)
    case head :: tail => {
      val map = recursiveGrouper(tail)
      map + (head.id -> List[Node]())
    }
  }

  def forwardMap(cs: List[Node]) = {
    val map: Map[String, List[Node]] = new HashMap[String, List[Node]]
//    cs.foldLeft(map)((m: Map[String, List[Node]], n: Node) => m ++ (n.id -> m))
        map
  }

  def graph(cs: List[Node]): List[GraphedNode] = {
    cs.map(GraphedNode(_, 0))
  }
}