import collection.mutable.ArrayBuffer

class HeightMap {
  val state = new ArrayBuffer[(Int, Range)]

  def add(height: Int, from: Long, to: Long) {
    state += ((height, from.asInstanceOf[Int] until to.asInstanceOf[Int] + 1))
    //    println("      --  height = " + height)
    //    println("      --  from = " + from)
    //    println("      --  to = " + to)
  }

  def heightAt(time: Long) = Iterable.max(0 :: state.filter(_._2 contains time).map(_._1).toList)

  def heightWithin(range: Range) = {
    //lolperformance
    Iterable.max(0 :: range.map(heightAt(_)).toList)
  }

  override def toString = {
    if (state.isEmpty) "" else {
      val min = Iterable.min(state.map(_._2.start))
      val max = Iterable.max(state.map(_._2.end))
      val maxHeight = Iterable.max(state.map(_._1))
      val lines = new ArrayBuffer[String]
      lines += "min: " + min + ", max: " + max + ", height: " + maxHeight
      Stream range (maxHeight, -1, -1) foreach ((i) => {
        val line = Array.make(101, ' ')
        state.filter(_._1 == i).foreach((x) => {
          //          println(List(x._1,x._2.start,x._2.end))
          //          println((140 * (x._2.start - min).toDouble / (max - min)))
          val l = (100 * (x._2.start - min).toDouble / (max - min)).round.toInt
          val r = (100 * (x._2.end - min).toDouble / (max - min)).round.toInt
          //          println("l: " + l + ", r: " + r)
          l until r + 1 foreach (x => {
            //            println((x,'-'))
            line.update(x, '-')
          })
        })
        lines += line.mkString
      })
      lines mkString "\n"
    }
  }
}