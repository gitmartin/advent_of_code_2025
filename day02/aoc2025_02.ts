import { readFileSync } from "fs";

const ranges = readFileSync("day02/input02", 'utf-8')
    .trim().split(",")
    .map(s => s.split('-').map(Number))
    
let part1 = 0
let part2 = 0
for (const [lo, hi] of ranges) {
    for (let n = lo; n <= hi; n++) {
        if (/^(.+)\1$/.test(n.toString())) part1 += n
        if (/^(.+)\1+$/.test(n.toString())) part2 += n
    }
}
console.log("Part 1:", part1)
console.log("Part 2:", part2)
