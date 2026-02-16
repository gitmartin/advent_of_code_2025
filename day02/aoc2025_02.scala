object Main02 {
    def main(args: Array[String]): Unit = {
        val ranges = scala.io.Source.fromFile("day02/input02")
        .getLines().toList(0).split(',')
        .map(_.split("-").map(_.toLong).toList)
        ranges.take(5).foreach(println)
        
        val (part1, part2) = 
        ranges
        .flatMap { case List(lo, hi) => lo to hi }
        .foldLeft(0L, 0L) { case ((a, b), n) =>
            (a + (if (n.toString.matches("(.+)\\1")) n else 0),
            b + (if (n.toString.matches("(.+)\\1+")) n else 0))
        }
        println("Part 1: " + part1)
        println("Part 2: " + part2)
    }
}