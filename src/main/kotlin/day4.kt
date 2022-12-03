fun main() {
    val sample1 = part1(readFileLines("day4/sample.txt"))
    check(sample1 == 100) { println(sample1) }
    val part1 = part1(readFileLines("day4/input.txt"))
    println("Part 1: $part1")

    val sample2 = part2(readFileLines("day4/sample.txt"))
    check(sample2 == 70) { println(sample2) }
    val part2 = part2(readFileLines("day4/input.txt"))
    println("Part 2: $part2")
}

private fun part1(lines: List<String>): Int {
    return 0
}

private fun part2(lines: List<String>): Int {
    return 0
}

