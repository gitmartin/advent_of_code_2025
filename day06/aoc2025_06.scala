import scala.collection.mutable.ListBuffer
object aoc2025_06 {
    def do_math(nums: Iterable[String], op: Char): Long = {
        op match 
            case '+' => nums.map(_.strip().toLong).sum
            case '*' => nums.map(_.strip().toLong).product
    }
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day06/input06").getLines.toArray
        // lines.foreach(x => println(x.mkString.take(150)))
        val matrix: Array[Array[String]] = lines.map(_.trim.split("\\s+"))
        val num_cols = matrix(1).length 

        val part1 = Range(0, num_cols).map { i =>
                val col = matrix.map(_(i))
                do_math(nums = col.dropRight(1), op = col.last(0))
            }.sum  
        println("Part 1: " + part1)

        var part2 = 0L
        var list: ListBuffer[String] = ListBuffer()
        for (i <- Range(lines(0).length -1, -1, -1)) {
            val nums: Array[Char] = lines.map(_(i))
            if (!nums.forall(_ == ' ')) {
                list += nums.dropRight(1).mkString
                if ("+*".contains(nums.last)) {
                    part2 += do_math(nums = list, nums.last)
                    list = ListBuffer()
                }
            }
        }
        println("Part 2: " + part2)
    }
}
