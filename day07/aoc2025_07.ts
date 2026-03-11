import { readFileSync } from "node:fs";

const lines = readFileSync('day07/input07', 'utf-8').trim().split("\n")
let beam_indices = new Map<number, number>()
beam_indices.set(lines[0].indexOf('S'), 1)

let part1 = 0
for (const line of lines.slice(1)) {
    let beam_indices_new = new Map<number, number>()
    for (const [idx, count] of beam_indices.entries()) {
        switch (line[idx]) {
            case '^':
                beam_indices_new.set(idx - 1, (beam_indices_new.get(idx - 1) ?? 0) + count)
                beam_indices_new.set(idx + 1, (beam_indices_new.get(idx + 1) ?? 0) + count)
                part1 += 1
                break;
            case '.':
                beam_indices_new.set(idx, (beam_indices_new.get(idx) ?? 0) + count)
                break;
        }
    }
    beam_indices = beam_indices_new
}
const part2 = Array.from(beam_indices.values()).reduce((x, y) => x + y)
console.log('Part 1:', part1)
console.log('Part 2:', part2)
