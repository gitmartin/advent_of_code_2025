import com.microsoft.z3.*

class Machine(line: String) {
    private val li = line.split("\\s+")
    val lights = li.head.slice(1, li.head.length()-1).map(_ == '#').toArray
    val buttons = li.slice(1, li.length -1)
        .map(s => s.slice(1, s.length -1).split(',').map(_.toInt))
    val joltages = li.last.slice(1, li.last.length-1).split(',').map(_.toInt)
    override def toString(): String =
        def fmt[T](arr: Array[T]): String = arr.mkString("[", ",", "]")
        s"""
        |lights: ${fmt(lights)}
        |buttons: ${fmt(buttons.map(fmt))}
        |joltages: ${fmt(joltages)}""".stripMargin
}
def solve_subsets(lights_target: Array[Boolean], buttons: Array[Array[Int]]): Int = {
    def toggleLights(buttons_to_push: Array[Array[Int]]): Array[Boolean] = {
        val lights = Array.fill(lights_target.length)(false)
        for (button <- buttons_to_push; b <- button) lights(b) = !lights(b)
        lights
    }
    Range(1, buttons.length + 1)
        .find(buttons
            .combinations(_)
            .exists(toggleLights(_) sameElements lights_target))
        .get
}
def solve_z3(machine: Machine): Long = {
    val ctx = new Context()
    val solver = ctx.mkOptimize()
    val numButtons = machine.buttons.length
    val numJoltages = machine.joltages.length
    // x(i) = number of times button i is pressed
    val x = Array.tabulate(numButtons)(i => ctx.mkIntConst(s"x$i"))
    // x(i) >= 0
    for (xi <- x) solver.Add(ctx.mkGe(xi, ctx.mkInt(0)))
    // M * x = j: for each joltage row, sum of x(col) where button col affects row == joltage(row)
    for (row <- 0 until numJoltages) {
        val terms = (0 until numButtons).collect {
            case col if machine.buttons(col).contains(row) => x(col)
        }
        val sum = if (terms.size == 1) terms.head
                  else ctx.mkAdd(terms*)
        solver.Add(ctx.mkEq(sum, ctx.mkInt(machine.joltages(row))))
    }
    // minimize sum(x)
    solver.MkMinimize(ctx.mkAdd(x*))
    solver.Check()
    val model = solver.getModel
    val result = x.map(xi => model.eval(xi, true).toString.toLong).sum
    ctx.close()
    result
}

def main(args: Array[String]): Unit = {
    val machines = scala.io.Source.fromFile("src/main/scala/input10")
        .getLines
        .map(Machine(_))
        .toList
    val part1 = machines.map(m => solve_subsets(m.lights, m.buttons)).sum
    println("Part 1: " + part1)
    val part2 = machines.map(solve_z3).sum
    println("Part 2: " + part2)
}
