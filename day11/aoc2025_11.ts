import { readFileSync } from "node:fs";

const graph = new Map<string, string[]>();
for (const line of readFileSync("day11/input11", "utf-8").trim().split("\n")) {
    const [key, rest] = line.split(":");
    graph.set(key, rest.trim().split(" "));
}
const cache = new Map<string, bigint>();
function numPaths(start: string, end: string): bigint {
    const key = `${start},${end}`;
    if (cache.has(key)) return cache.get(key)!;
    const result = start === end
        ? BigInt(1)
        : (graph.get(start) ?? []).reduce((sum, n) => sum + numPaths(n, end), BigInt(0));
    cache.set(key, result);
    return result;
}
console.log(`Part 1: ${numPaths("you", "out")}`);

const part2 = numPaths("svr", "dac") * numPaths("dac", "fft") * numPaths("fft", "out") +
              numPaths("svr", "fft") * numPaths("fft", "dac") * numPaths("dac", "out");
console.log(`Part 2: ${part2}`);
