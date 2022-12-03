// a - z -> 1 - 26
// A - Z -> 27 - 52
fun main() {
    val sample1 = part1(readFileLines("day3/sample.txt"))
    check(sample1 == 157)
    val part1 = part1(readFileLines("day3/input.txt"))
    println("Part 1: $part1")

    val sample2 = part2(readFileLines("day3/sample.txt"))
    check(sample2 == 70)
    val part2 = part2(readFileLines("day3/input.txt"))
    println("Part 2: $part2")
}

private fun part1(lines: List<String>): Int {
    return lines.map {
        val comp1: String = it.slice(0 until it.length / 2)
        val comp2: String = it.slice(it.length / 2..it.lastIndex)
        val comp1Priorities: Set<Int> = comp1.toList().map { it.toPriority() }.toSet()
        val comp2Priorities: Set<Int> = comp2.toList().map { it.toPriority() }.toSet()
        comp1Priorities.intersect(comp2Priorities).sum()
    }
        .sum()
}

private fun part2(lines: List<String>): Int {
    var sum = 0
    for (i in 2..lines.lastIndex step 3) {
        sum += lines.slice(i - 2..i)
            .map { it.toSet() }
            .reduce { acc, curr -> acc.intersect(curr) }
            .sumOf { it.toPriority() }
    }
    return sum
}

fun Char.toPriority(): Int {
    return if (this.isLowerCase()) {
        this.code - 96
    } else {
        this.code - 38
    }
}
