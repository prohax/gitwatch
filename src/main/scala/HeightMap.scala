import collection.mutable.ArrayBuffer

class HeightMap {
  val state = new ArrayBuffer[(Int,Range)]

  def add(height: Int, from: Long, to: Long) {
    state +=((height, from.asInstanceOf[Int] until to.asInstanceOf[Int] + 1))
//    println("      --  height = " + height)
//    println("      --  from = " + from)
//    println("      --  to = " + to)
  }

  def currentMax(time: Long) = {
    val max = Iterable.max(0 :: state.filter(_._2 contains time).map(_._1).toList)
//    println("      --  time = " + time)
//    println("      --  max = " + max)
    max
  }
}