import { readFileSync } from "node:fs";

function count_neighbors(grid: string[][], r: number, c: number): number {
    let cnt = 0
    for (const dr of [-1, 0, 1]) {
        for (const dc of [-1, 0, 1]) {
            if (dr === 0 && dc === 0) continue
            const rr = r + dr
            const cc = c + dc
            if (0 <= rr && rr < grid.length
                && 0 <= cc && cc < grid[0].length
                && grid[rr][cc] === '@') cnt += 1
        }
    }
    return cnt
}
const grid = readFileSync("day04/input04", "utf-8")
    .trim().split("\n")
    .map(x => x.split(""))

let roll_cnt = 0
for (let r = 0; r < grid.length; r++) {
    for (let c = 0; c < grid[0].length; c++) {
        if (grid[r][c] === '@' && count_neighbors(grid, r, c) < 4) roll_cnt += 1
    }
}
console.log("Part 1:", roll_cnt)

let total_change = 0
while (true) {
    let change = 0
    for (let r = 0; r < grid.length; r++) {
        for (let c = 0; c < grid[0].length; c++) {
            if (grid[r][c] === '@' && count_neighbors(grid, r, c) < 4) {
                grid[r][c] = '.'
                change += 1
            }
        }
    }
    total_change += change
    if (change === 0) break
}
console.log('Part 2:', total_change)
