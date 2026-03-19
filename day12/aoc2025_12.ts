import { readFileSync } from "node:fs";

const blocks = readFileSync("day12/input12", "utf-8")
    .trim()
    .split("\n\n");

const regionBlocks = blocks.filter(b => b.includes("x"));
const shapeBlocks = blocks.filter(b => !b.includes("x"));
const shapeAreas = shapeBlocks.map(b => b.split("").filter(c => c === "#").length);
const regions = regionBlocks.flatMap(b => b.split("\n"));
const count = regions.filter(line => {
    const [dimPart, countsPart] = line.split(":");
    const regionSize = dimPart
        .split("x")
        .map(Number)
        .reduce((a, b) => a * b, 1);
    const counts = countsPart.trim().split(/\s+/).map(Number);
    const totalShapeArea = counts.reduce((sum, n, i) => sum + n * shapeAreas[i], 0);
    return totalShapeArea <= regionSize;
}).length;

console.log("Part 1:", count);
