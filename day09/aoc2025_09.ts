import { readFileSync } from "node:fs";
type Point = { x: number; y: number }
type Segment = { s1: Point; s2: Point }

const points: Point[] = readFileSync('day09/input09', 'utf-8')
    .trim()
    .split('\n')
    .map(_ => _.split(',').map(Number))
    .map(a => ({ x: a[0], y: a[1] }))

function area(p1: Point, p2: Point): number {
    return (1 + Math.abs(p2.y - p1.y)) * (1 + Math.abs(p2.x - p1.x))
}
let max_area = -1
for (let i = 0; i < points.length; i++) {
    for (let j = i + 1; j < points.length; j++) {
        const A = area(points[i], points[j])
        max_area = Math.max(max_area, A)
    }
}
console.log('Part 1:', max_area)

const segments: Segment[] =
    points.map((p, i) => ({ s1: p, s2: points[(i + 1) % points.length] }))
max_area = -1
for (let i = 0; i < points.length; i++) {
    for (let j = i + 1; j < points.length; j++) {
        const [p1, p2] = [points[i], points[j]]
        const [bottom, top] = (p1.y <= p2.y) ? [p1.y, p2.y] : [p2.y, p1.y]
        const [left, right] = (p1.x <= p2.x) ? [p1.x, p2.x] : [p2.x, p1.x]
        const discard = segments.some(seg => {
            if (seg.s1.y === seg.s2.y) {
                return bottom < seg.s1.y && seg.s1.y < top &&
                    Math.min(seg.s1.x, seg.s2.x) < right &&
                    Math.max(seg.s1.x, seg.s2.x) > left
            } else {
                return left < seg.s1.x && seg.s1.x < right &&
                    Math.min(seg.s1.y, seg.s2.y) < top &&
                    Math.max(seg.s1.y, seg.s2.y) > bottom
            }
        })
        if (!discard) max_area = Math.max(max_area, area(p1, p2))
    }
}
console.log('Part 2:', max_area)
