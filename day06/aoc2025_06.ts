import { readFileSync } from "node:fs";

function do_math(nums: string[], op: string): number {
    switch (op) {
        case '+':
            return nums.map(x => x.trim()).map(Number).reduce((x, y) => x + y)
        case '*':
            return nums.map(x => x.trim()).map(Number).reduce((x, y) => x * y)
        default:
            throw new Error(`Unknown op: ${op}`)
    }
}

const lines: string[] = readFileSync("day06/input06", "utf-8")
    .split("\n")
    .slice(0, -1)

const matrix: string[][] = lines.map(x => x.trim().split(/\s+/))
const num_cols = matrix[0].length
let part1 = 0
for (let i = 0; i < num_cols; i++)
    part1 += do_math(matrix.slice(0, -1).map(x => x[i]), matrix[matrix.length - 1][i])
console.log('Part 1:', part1)

let part2 = 0
let stack: string[] = []
for (let i = lines[0].length - 1; i >= 0; i--) {
    const nums = lines.map(x => x[i])
    if (nums.every(c => c === ' ')) continue
    stack.push(nums.slice(0, -1).join(''))
    if ('+*'.includes(nums[nums.length - 1])) {
        part2 += do_math(stack, nums[nums.length - 1])
        stack = []
    }
}
console.log('Part 2:', part2)
