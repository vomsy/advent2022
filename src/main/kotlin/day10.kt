fun main() {
//    val small = part1(readFileLines("day10/sample.txt"));

    val sample1 = part1(readFileLines("day10/larger.txt"))
    want(sample1, 13140)
    val part1 = part1(readFileLines("day10/input.txt"))
    println("Part 1: $part1")
    want(part1, 13060)

    val sample2 = part2(readFileLines("day10/sample.txt"))
    want(sample2, 1)
    val part2 = part2(readFileLines("day10/input.txt"))
    println("Part 2: $part2")
    want(part2, 2793)
}

private fun part1(lines: List<String>): Int {
    var curValue = 1
    val cycles = mutableListOf<Int>()
    for (line in lines) {
        val split = line.split(" ")
        val instruction = split[0]
        if (instruction == "noop") {
            cycles.add(curValue)
        } else{
            val addValue = split[1].toInt()
            cycles.add(curValue)
            cycles.add(curValue)
            curValue += addValue
        }
    }

    var result = 0
    for (i in 19..219 step 40) {
        val sigStrength = cycles[i] * (i + 1)
        println("cycle ${i+1} * ${cycles[i]} = $sigStrength")
        result += sigStrength
    }
    return result
}


private fun part2(readFileLines: List<String>): Int {
    return 0
}
