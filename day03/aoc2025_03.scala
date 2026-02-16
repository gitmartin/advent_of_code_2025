object Main03 {
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day03/input03")
        .getLines().toList
        
        println("Part 1: " + lines.map(solve_rec(_, 2).toLong).sum)
        println("Part 2: " + lines.map(solve_rec(_, 12).toLong).sum)
    }
    
    def solve_rec(line: String, num_digits: Int): String = {
        if (num_digits == 1) return line.max.toString
        val maxi = line.dropRight(num_digits - 1).max
        maxi + solve_rec(
        line = line.substring(line.indexOf(maxi) + 1),
        num_digits = num_digits - 1)
    }
}
