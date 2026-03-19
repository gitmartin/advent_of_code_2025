def main(args: Array[String]): Unit = {
    val blocks = scala.io.Source
        .fromFile("src/main/scala/input12")
        .mkString
        .split("\n\n")
    val (regionBlocks, shapeBlocks) = blocks.partition(_.contains("x"))
    val shapeAreas = shapeBlocks.map(_.count(_ == '#'))
    val regions = regionBlocks.flatMap(_.split("\n"))
    val count = regions.count { line =>
        val parts = line.split(":")
        val regionSize = parts(0).split("x").map(_.toInt).product
        val counts = parts(1).trim.split("\\s+").map(_.toInt)
        val totalShapeArea = counts.zip(shapeAreas).map { case (n, a) => n * a }.sum
        totalShapeArea <= regionSize
    }
    println("Part 1: " + count)
}
