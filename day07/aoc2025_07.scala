import scala.collection.mutable
object Main07 {
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day07/input07").getLines().toList
        var beam_indices = mutable.Map[Int, Long](lines(0).indexOf('S') -> 1)
        var part1 = 0
        for (line <- lines.tail)
            val beam_indices_new = mutable.Map[Int, Long]().withDefaultValue(0)
            for ((idx, count) <- beam_indices)
                line(idx) match
                    case '^' =>
                        beam_indices_new(idx - 1) += count
                        beam_indices_new(idx + 1) += count
                        part1 += 1
                    case '.' =>
                        beam_indices_new(idx) += count
            beam_indices = beam_indices_new
        println("Part 1: " + part1)
        println("Part 2: " + beam_indices.values.sum)
    }
}
