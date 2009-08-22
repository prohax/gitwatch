import scala.Random
import org.specs._

object GrapherTest extends Specification {
  val single = List(Node("a", Nil, 1000))

  val trunkOnly = List(
    Node("a", Nil, 1000),
    Node("b", List("a"), 1001),
    Node("c", List("b"), 1002))

  val brancher = List(
    Node("a", Nil, 1000),
    Node("b", List("a"), 1001),
    Node("c", List("a"), 1002))

  val merger = List(
    Node("a", Nil, 1000),
    Node("b", Nil, 1001),
    Node("c", List("a", "b"), 1002))

  val ambitious = List(
    Node("a", Nil, 1001),
    Node("e", List("d", "h"), 1001),
    Node("d", List("g", "c"), 1002)) ++
          List(("b", "a"), ("c", "b"), ("f", "b"), ("g", "f"), ("h", "f")).
                  map((x) => Node(x._1, List(x._2), 1000))

  "forward mapper" should {
    "maintain an empty input" in {
      Grapher.forwardGraph(Nil) must beEqualTo(Map(
        "INIT" -> Nil
        ))
    }
    "maintain a single input" in {
      val List(a) = single
      Grapher.forwardGraph(single) must beEqualTo(Map(
        "INIT" -> List(a)
        ))
    }
    "maintain a single trunk" in {
      val List(a, b, c) = trunkOnly
      Grapher.forwardGraph(trunkOnly) must beEqualTo(Map(
        "INIT" -> List(a),
        "a" -> List(b),
        "b" -> List(c)
        ))
    }
    "handle a branch" in {
      val List(a, b, c) = brancher
      Grapher.forwardGraph(brancher) must beEqualTo(Map(
        "INIT" -> List(a),
        "a" -> List(b, c)
        ))
    }
    "handle a merge" in {
      val List(a, b, c) = merger
      Grapher.forwardGraph(merger) must beEqualTo(Map(
        "INIT" -> List(a, b),
        "a" -> List(c),
        "b" -> List(c)
        ))
    }
    "handle an ambitious case" in {
      val List(a, e, d, b, c, f, g, h) = ambitious
      Grapher.forwardGraph(ambitious) must beEqualTo(Map(
        "INIT" -> List(a),
        "a" -> List(b),
        "b" -> List(c, f),
        "c" -> List(d),
        "d" -> List(e),
        "f" -> List(g, h),
        "g" -> List(d),
        "h" -> List(e)
        ))
    }
  }

  "grapher main" should {
//    "maintain an empty input" in {
//        Grapher.graph(List[Node](), "missing")
//    }
    "maintain a single input" in {
      val graph = Grapher.graph(single, "a")
      graph must beEqualTo(single.map(GraphedNode(_, 0)))
    }
    "maintain a single trunk" in {
      val graph = Grapher.graph(trunkOnly, "c")
      graph must haveSameElementsAs(trunkOnly.map(GraphedNode(_, 0)))
    }
    "lift a branch" in {
      val List(a, b, c) = brancher
      Grapher.graph(brancher, "b") must haveSameElementsAs(List(
        GraphedNode(a, 0),
        GraphedNode(b, 0),
        GraphedNode(c, 1)
        ))
    }
    "lift a merge" in {
      val List(a, b, c) = merger
      Grapher.graph(merger, "c") must haveSameElementsAs(List(
        GraphedNode(a, 0),
        GraphedNode(b, 1),
        GraphedNode(c, 0)
        ))
    }
  }
}
