object Main01 {
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day01/input01")
        .getLines().toList
        .map(_.replace('L', '-').replace("R", "").toInt)
        println(lines.take(5))
        
        var zeros_part1 = 0
        var zeros_part2 = 0
        var pos = 50
        for (n <- lines) {
            if (n < 0) pos = (100 - pos) % 100
            pos += math.abs(n)
            zeros_part2 += pos / 100
            pos = pos % 100
            if (n < 0) pos = (100 - pos) % 100
            if (pos == 0) zeros_part1 += 1
        }
        println("Part 1: " + zeros_part1)
        println("Part 2: " + zeros_part2)
    }
}