import { readFileSync } from "node:fs";

const lines = readFileSync("day05/input05", "utf-8").trim().split("\n")
const separator = lines.indexOf('')
const intervals: number[][] = lines.slice(0, separator)
    .map(s => s.split('-').map(Number))
const nums: number[] = lines.slice(separator + 1).map(Number)

const num_contained = nums
    .filter(n => intervals.some(arr => arr[0] <= n && n <= arr[1]))
    .length
console.log('Part 1:', num_contained)

intervals.sort((a, b) => a[0] - b[0])
for (let i = 1; i < intervals.length; i++) {
    if (intervals[i][0] <= intervals[i - 1][1]) {
        intervals[i][0] = intervals[i - 1][0]
        intervals[i][1] = Math.max(intervals[i][1], intervals[i - 1][1])
        intervals[i - 1] = []
    }
}
const part2 = intervals
    .map(x => x.length === 0 ? 0 : x[1] - x[0] + 1)
    .reduce((x, y) => x + y)
console.log('Part 2:', part2)
