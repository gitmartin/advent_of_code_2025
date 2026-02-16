object aoc2025_05 {
    def main(args: Array[String]): Unit = {
        val lines = scala.io.Source.fromFile("day05/input05").getLines().toArray
        val (intervals: Array[Array[Long]], nums: Array[Long]) = 
        lines.splitAt(lines.indexOf("")) match {
            case (a, b) => (a.map(_.split("-").map(_.toLong)), 
            b.tail.map(_.toLong))
        }
        
        val num_contained = nums.count(n => intervals.exists(arr => arr(0) <= n && n <= arr(1)))
        println("Part 1: " + num_contained)
        
        intervals.sortInPlaceBy(_(0))
        for (i <- Range(1, intervals.length)) {
            if (intervals(i)(0) <= intervals(i-1)(1)) {
                intervals(i)(0) = intervals(i-1)(0)
                intervals(i)(1) =  intervals(i)(1).max(intervals(i-1)(1))
                intervals(i-1) = null
            }
        }
        println("Part 2: " + 
        intervals.map(x => if (x == null) 0 else x(1) - x(0) + 1).sum)
    }
}
