def distance3d(a: Array[Long], b: Array[Long]): Double = 
    Range(0,3).map(i => math.pow(a(i)-b(i), 2)).sum

def main(args: Array[String]): Unit = {
    val points = scala.io.Source.fromFile("day08/input08").getLines().toVector
    .map(_.split(",").map(_.toLong).toArray)

    val edges: Vector[(Int, Int)] = points
    .zipWithIndex
    .combinations(2)
    .map { case Vector(p1, p2) => (p1._2, p2._2, distance3d(p1._1, p2._1)) }
    .toVector.sortBy(_._3)
    .map(x => (x._1, x._2)) 

    // Union-Find
    var parents = Range(0, points.length).toArray
    def find(i: Int): Int = if (i == parents(i)) i else find(parents(i))
    def union(i: Int, j: Int): Boolean = {
        val i_find = find(i)
        val j_find = find(j)
        parents(i_find) = j_find
        i_find == j_find
    }
    edges.take(1000).foreach { case (i, j) => union(i, j) }
    val part1 = parents.map(find).groupBy(identity).mapValues(_.size)
    .values.toVector.sorted.takeRight(3).product
    println("Part 1: " + part1)

    parents = Range(0, points.length).toArray // reset for part 2
    var num_clusters = points.length
    var edge_idx = -1
    while (num_clusters != 1) {
        edge_idx += 1
        if (!union(edges(edge_idx)(0), edges(edge_idx)(1))) num_clusters -= 1
    }
    println("Part 2: " + points(edges(edge_idx)(0))(0) * 
                         points(edges(edge_idx)(1))(0))
}
