fun main() {
    check(part1(readFileAsStr("day1/sample.txt")) == 24000)
    val part1Result = part1(readFileAsStr("day1/input.txt"))
    println("part1 = $part1Result")


    check(part2(readFileAsStr("day1/sample.txt")) == 45000)
    val part2Result = part2(readFileAsStr("day1/input.txt"))
    println("part2 = $part2Result")
}

private fun part1(input: String): Int {
    val split: List<String> = input.split("\n\n")
    return split
        .map { s ->
            s.split("\n")
                .filter { it.isNotBlank() }
                .sumOf { it.toInt() }
        }
        .max()
}

private fun part2(input: String): Int {
    val split: List<String> = input.split("\n\n")
    val elvesSorted = split
        .map { s ->
            s.split("\n")
                .filter { it.isNotBlank() }
                .sumOf { it.toInt() }
        }
        .sortedDescending()
    return elvesSorted.slice(0..2).sum()
}
