import { readFileSync } from "node:fs";

function max_str(s: string): string {
    return s.split("").reduce((a, b) => a > b ? a : b)
}
function solve_rec(line: string, num_digits: number): string {
    if (num_digits === 1) return max_str(line)
    const maxi: string = max_str(line.slice(0, -num_digits + 1))
    return maxi + solve_rec(line.slice(line.indexOf(maxi) + 1), num_digits - 1)
}
const lines = readFileSync("day03/input03", 'utf-8').trim().split("\n")

const part1 = lines.map(x => solve_rec(x, 2)).map(Number).reduce((a, b) => a + b, 0)
const part2 = lines.map(x => solve_rec(x, 12)).map(Number).reduce((a, b) => a + b, 0)
console.log("Part 1:", part1)
console.log("Part 2:", part2)
