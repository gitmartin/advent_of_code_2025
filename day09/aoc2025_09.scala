case class Point(x: Long, y: Long)
case class Segment(s1: Point, s2: Point)

def area(p1: Point, p2: Point): Long =
    (1 + Math.abs(p2.y - p1.y)) * (1 + Math.abs(p2.x - p1.x))

def main(args: Array[String]): Unit = {
    val points = scala.io.Source.fromFile("day09/input09")
        .getLines.toList
        .map(_.split(',').map(_.toLong))
        .map(a => Point(a(0), a(1)))
    println("Part 1: " + points.combinations(2).map { case Seq(p1, p2) => area(p1, p2) }.max)

    val segments = points.indices.map { i =>
        Segment(points(i), points((i + 1) % points.length))
    }
    val part2 = points.combinations(2).filter { case Seq(p1, p2) =>
        val (bottom, top) = if (p1.y <= p2.y) (p1.y, p2.y) else (p2.y, p1.y)
        val (left, right) = if (p1.x <= p2.x) (p1.x, p2.x) else (p2.x, p1.x)
        !segments.exists { seg =>
            if (seg.s1.y == seg.s2.y) // horizontal
                bottom < seg.s1.y && seg.s1.y < top &&
                seg.s1.x.min(seg.s2.x) < right && seg.s1.x.max(seg.s2.x) > left
            else // vertical
                left < seg.s1.x && seg.s1.x < right &&
                seg.s1.y.min(seg.s2.y) < top && seg.s1.y.max(seg.s2.y) > bottom
        }
    }.map { case Seq(p1, p2) => area(p1, p2) }.max
    println("Part 2: " + part2)
}
