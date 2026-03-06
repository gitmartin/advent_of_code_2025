import { readFileSync } from "fs";

const lines = readFileSync("day01/input01", "utf-8")
    .trim().split("\n")
    .map(x => parseInt(x.replace("L", "-").replace("R", "")))
console.log(lines.slice(0, 5))

let zeros_part1 = 0
let zeros_part2 = 0
let pos = 50
for (const n of lines) {
    if (n < 0) pos = (100 - pos) % 100
    pos += Math.abs(n)
    zeros_part2 += Math.floor(pos / 100)
    pos %= 100
    if (n < 0) pos = (100 - pos) % 100
    if (pos == 0) zeros_part1 += 1
}
console.log("Part 1:", zeros_part1)
console.log("Part 2:", zeros_part2)
