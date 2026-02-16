object Main04 {
    def count_neighbors(grid: Array[Array[Char]], r: Int, c: Int): Int = {
        var cnt = 0
        for (dr <- List(-1, 0, 1); dc <- List(-1, 0, 1))
            if (!(dr == 0 && dc == 0))
                val rr = r + dr
                val cc = c + dc
                if (0 <= rr && rr < grid.length
                && 0 <= cc && cc < grid(0).length
                && grid(rr)(cc) == '@') cnt += 1
        cnt
    }
    def part1(grid: Array[Array[Char]]): Int = {
        var roll_cnt = 0
        for (r <- Range(0, grid.length); c <- Range(0, grid(0).length))
            if (grid(r)(c) == '@' && count_neighbors(grid, r, c) < 4)
            roll_cnt += 1
        roll_cnt
    }
    def part2(grid: Array[Array[Char]]): Int = {
        var total_change = 0
        while (true)
            var change = 0
            for (r <- Range(0, grid.length); c <- Range(0, grid(0).length)) 
                if (grid(r)(c) == '@' && count_neighbors(grid, r, c) < 4) 
                    grid(r)(c) = '.'
                    change += 1
            total_change += change
            if (change == 0) return total_change
        0
    }
    def main(args: Array[String]): Unit = {
        val grid: Array[Array[Char]] = scala.io.Source.fromFile("day04/input04")
        .getLines().toArray.map(_.toArray)
        println("Part 1: " + part1(grid))
        println("Part 2: " + part2(grid))
    }
}
