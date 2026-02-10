import scala.collection.mutable
object Main07 {
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day07/input07").getLines().toList
        var beam_indeces = mutable.Map[Int, Long](lines(0).indexOf('S') -> 1)
        var part1 = 0
        for (line <- lines.tail)
            val beam_indeces_new = mutable.Map[Int, Long]().withDefaultValue(0)
            for ((idx, count) <- beam_indeces)
                line(idx) match
                    case '^' =>
                        beam_indeces_new(idx - 1) += count
                        beam_indeces_new(idx + 1) += count
                        part1 += 1
                    case '.' =>
                        beam_indeces_new(idx) += count
            beam_indeces = beam_indeces_new
        println("Part 1: " + part1)
        println("Part 2: " + beam_indeces.values.sum)
    }
}
