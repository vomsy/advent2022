fun main() {
    val sample1 = part1(readFileLines("day5/sample.txt"))
    check(sample1 == "CMZ") { println(sample1) }
    val part1 = part1(readFileLines("day5/input.txt"))
    println("Part 1: $part1")

    val sample2 = part2(readFileLines("day5/sample.txt"))
    check(sample2 == "MCD") { println(sample2) }
    val part2 = part2(readFileLines("day5/input.txt"))
    println("Part 2: $part2")
}

private fun part1(lines: List<String>): String {
    return solve(lines, ::crane9000)
}

private fun part2(lines: List<String>): String {
    return solve(lines, ::crane9001)
}

private fun solve(
    lines: List<String>,
    crane: (stacks: List<ArrayDeque<String>>, instructions: List<Int>) -> Unit
): String {
    val sep = lines.indexOf("")
    val stackLines = lines.subList(0, sep)
    val movesLines = lines.subList(sep + 1, lines.size)

    val stacks = buildStack(stackLines)
    moveStacks(stacks, movesLines, crane)

    return stacks.map { it.last() }.joinToString("")
}

fun moveStacks(
    stacks: List<ArrayDeque<String>>,
    movesLines: List<String>,
    crane: (stacks: List<ArrayDeque<String>>, instructions: List<Int>) -> Unit
) {
    for (line in movesLines) {
        val instructions = line.replace("move", "")
            .replace("from", "")
            .replace("to", "").split(" ")
            .filterNot { it.isBlank() }
            .map { it.toInt() }

        crane(stacks, instructions)
    }
}

private fun crane9000(stacks: List<ArrayDeque<String>>, instructions: List<Int>) {
    for (m in 1..instructions[0]) {
        val item = stacks[instructions[1] - 1].removeLast()
        stacks[instructions[2] - 1].addLast(item)
    }
}

private fun crane9001(stacks: List<ArrayDeque<String>>, instructions: List<Int>) {
    val temp = ArrayDeque<String>()
    for (m in 1..instructions[0]) {
        val item = stacks[instructions[1] - 1].removeLast()
        temp.addLast(item)
    }

    while (temp.isNotEmpty()) {
        stacks[instructions[2] - 1].addLast(temp.removeLast())
    }
}

private fun buildStack(lines: List<String>): List<ArrayDeque<String>> {
    val stacksCount = lines.last().trim().split("\\s+".toRegex()).size
    val stacks = ArrayList<ArrayDeque<String>>(stacksCount)

    for (i in 1..stacksCount) {
        val idx = i + 3 * i - 3

        val toList = lines
            .mapNotNull { it.getOrNull(idx) }
            .filter { it.isLetter() }
            .toList()

        val stack = ArrayDeque<String>()
        toList.forEach { stack.addFirst(it.toString()) }
        stacks.add(stack)
    }

    return stacks
}
