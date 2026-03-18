import { readFileSync } from "node:fs";
import { init } from "z3-solver";

class Machine {
    lights: boolean[]
    buttons: number[][]
    joltages: number[]
    constructor(line: string) {
        const li = line.split(/\s+/)
        this.lights = [...li[0].slice(1, -1)].map(x => x === '#')
        this.buttons = li.slice(1, -1)
            .map(s => s.slice(1, -1).split(',').map(Number))
        this.joltages = li[li.length - 1].slice(1, -1).split(',').map(Number)
    }
}
function* combinations<T>(arr: T[], k: number, start = 0): Generator<T[]> {
    if (k === 0) {
        yield [];
        return;
    }
    for (let i = start; i <= arr.length - k; i++) {
        for (const rest of combinations(arr, k - 1, i + 1)) {
            yield [arr[i], ...rest];
        }
    }
}
function solve_subsets(machine: Machine): number {
    for (let subset_size = 1; subset_size < machine.buttons.length + 1; subset_size++) {
        for (let buttons_to_push of combinations(machine.buttons, subset_size)) {
            const lights = Array(machine.lights.length).fill(false)
            for (const button of buttons_to_push) {
                for (const b of button) lights[b] = !lights[b]
            }
            if (lights.every((v, i) => v === machine.lights[i]))
                return subset_size
        }
    }
}
const machines = readFileSync("day10/input10", "utf-8")
    .trim()
    .split("\n")
    .map(s => new Machine(s));

console.log('Part 1:', machines.map(solve_subsets).reduce((x, y) => x + y))

const z3Init = init();
async function solve_z3(machine: Machine): Promise<number> {
    const { Context } = await z3Init;
    const z3 = Context('main');
    const solver = new z3.Optimize();
    const numButtons = machine.buttons.length;
    const x = Array.from({ length: numButtons }, (_, i) => z3.Int.const(`x${i}`));
    for (const xi of x) solver.add(xi.ge(0));
    for (let row = 0; row < machine.joltages.length; row++) {
        const terms = x.filter((_, col) => machine.buttons[col].includes(row));
        const sum = terms.length === 1 ? terms[0] : z3.Sum(terms[0], ...terms.slice(1));
        solver.add(sum.eq(machine.joltages[row]));
    }
    solver.minimize(z3.Sum(x[0], ...x.slice(1)));
    await solver.check();
    const model = solver.model();
    return x.reduce((acc, xi) => acc + Number(model.eval(xi).toString()), 0);
}
Promise.all(machines.map(solve_z3)).then(results => {
    console.log('Part 2:', results.reduce((x, y) => x + y));
});
