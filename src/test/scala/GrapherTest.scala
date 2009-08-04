import org.specs._

object GrapherTest extends Specification {
  "grapher main" should {
    "maintain an empty input" in {
	    val graph = Grapher.graph(List[Node]())
      graph must be empty
    }
    "maintain a single input" in {
	    val a = Node("a", Nil, 1000)
	    val graph = Grapher.graph(List(a))
      graph must beEqualTo(List(GraphedNode(a,0)))
    }
    "maintain a single trunk" in {
	    val a = Node("a", Nil, 1000)
      val b = Node("b", List("a"), 1001)
      val c = Node("c", List("b"), 1002)
	    val graph = Grapher.graph(List(a,b,c))
      graph must beEqualTo(List(GraphedNode(a,0),GraphedNode(b,0),GraphedNode(c,0)))
    }    
    "raise a single branch" in {
	    val a = Node("a", Nil, 1000)
      val b = Node("b", List("a"), 1001)
      val c = Node("c", List("b"), 1002)
      val d = Node("d", List("b"), 1003)
	    val graph = Grapher.graph(List(a,b,c,d))
      graph must beEqualTo(List(GraphedNode(a,0),GraphedNode(b,0),GraphedNode(c,0),GraphedNode(d,1)))
    }
  }
}
