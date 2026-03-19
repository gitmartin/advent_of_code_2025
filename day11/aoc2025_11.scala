import scala.collection.mutable

def main(args: Array[String]): Unit = {
    val graph: Map[String, List[String]] = scala.io.Source.fromFile("src/main/scala/input11")
        .getLines().map { line =>
            val parts = line.split(":")
            parts(0) -> parts(1).trim.split(" ").toList
        }.toMap

    val cache = mutable.Map[(String, String), BigInt]()
    def numPaths(start: String, end: String): BigInt = cache.getOrElseUpdate((start, end),
        if (start == end) BigInt(1)
        else graph.getOrElse(start, Nil).map(n => numPaths(n, end)).sum
    )
    println(s"Part 1: ${numPaths("you", "out")}")

    val part2 = numPaths("svr", "dac") * numPaths("dac", "fft") * numPaths("fft", "out") +
                numPaths("svr", "fft") * numPaths("fft", "dac") * numPaths("dac", "out")
    println(s"Part 2: $part2")
}
