fun main() {
    val sample1 = part1(readFileAsStr("day6/sample.txt"))
    check(sample1 == 10) { println(sample1) }
    val part1 = part1(readFileAsStr("day6/input.txt"))
    println("Part 1: $part1")

    val sample2 = part2(readFileAsStr("day6/sample.txt"))
    check(sample2 == 29) { println(sample2) }
    val part2 = part2(readFileAsStr("day6/input.txt"))
    println("Part 2: $part2")
}

private fun part1(input: String): Int = solve(input, 4)

private fun part2(input: String): Int = solve(input, 14)

private fun solve(input: String, size: Int): Int {
    var idx = size - 1;

    for (i in (size - 1)..input.lastIndex) {
        val four = input.slice(i - (size - 1)..i)
        if (four.toSet().size == size) {
            idx = i
            break
        }
        idx++
    }

    return idx + 1
}
