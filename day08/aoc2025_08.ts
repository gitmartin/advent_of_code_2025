import { readFileSync } from "node:fs";

function distance3d(a: number[], b: number[]): number {
    return [0, 1, 2]
        .map(i => (a[i] - b[i]) ** 2)
        .reduce((x, y) => x + y)
}

const points: number[][] = readFileSync('day08/input08', 'utf-8')
    .trim()
    .split('\n')
    .map(x => x.split(',').map(Number))

const edges: [number, number, number][] = []
for (let i = 0; i < points.length; i++) {
    for (let j = i + 1; j < points.length; j++) {
        edges.push([i, j, distance3d(points[i], points[j])])
    }
}
edges.sort((a, b) => a[2] - b[2]) // sort by distance

// union-find
let parents = Array.from({ length: points.length }, (_, i) => i);
function find(i: number): number {
    return i === parents[i] ? i : find(parents[i])
}
function union(i: number, j: number): boolean {
    const i_find = find(i)
    const j_find = find(j)
    parents[i_find] = j_find
    return i_find === j_find
}
edges.slice(0, 1000).forEach(([i, j]) => union(i, j))

const sizes = new Map<number, number>()
for (const p of parents) {
    const findp = find(p)
    sizes.set(findp, (sizes.get(findp) ?? 0) + 1)
}
const part1 = Array.from(sizes.values())
    .sort((a, b) => a - b)
    .slice(-3)
    .reduce((x, y) => x * y)
console.log('Part 1:', part1)

parents = Array.from({ length: points.length }, (_, i) => i); // reset for part 2
let num_clusters = points.length
let edge_idx = -1
while (num_clusters !== 1) {
    edge_idx++
    const [u, v] = edges[edge_idx]
    if (!union(u, v)) num_clusters--
}
console.log('Part 2:', points[edges[edge_idx][0]][0] *
    points[edges[edge_idx][1]][0])
