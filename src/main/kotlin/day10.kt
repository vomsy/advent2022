fun main() {
//    val small = part1(readFileLines("day10/sample.txt"));

    val sample1 = part1(readFileLines("day10/larger.txt"))
    want(sample1, 13140)
    val part1 = part1(readFileLines("day10/input.txt"))
    println("Part 1: $part1")
    want(part1, 13060)

    val sample2 = part2(readFileLines("day10/larger.txt"))
    val expectedCrt = "##..##..##..##..##..##..##..##..##..##..\n" +
            "###...###...###...###...###...###...###.\n" +
            "####....####....####....####....####....\n" +
            "#####.....#####.....#####.....#####.....\n" +
            "######......######......######......####\n" +
            "#######.......#######.......#######....."

    want(sample2, expectedCrt)
    val part2 = part2(readFileLines("day10/input.txt"))
    println("$part2")

    want(
        part2, "####...##.#..#.###..#..#.#....###..####.\n" +
                "#.......#.#..#.#..#.#..#.#....#..#....#.\n" +
                "###.....#.#..#.###..#..#.#....#..#...#..\n" +
                "#.......#.#..#.#..#.#..#.#....###...#...\n" +
                "#....#..#.#..#.#..#.#..#.#....#.#..#....\n" +
                "#.....##...##..###...##..####.#..#.####."
    )

}

private fun part2(lines: List<String>): String {
    var curValue = 1
    val cycles = mutableListOf<Int>()
    for (line in lines) {
        val split = line.split(" ")
        val instruction = split[0]
        if (instruction == "noop") {
            cycles.add(curValue)
        } else {
            val addValue = split[1].toInt()
            cycles.add(curValue)
            cycles.add(curValue)
            curValue += addValue
        }
    }

    var crt = ""
    for ((c, regVal) in cycles.withIndex()) {
        if (c in 40..240 step 40) {
            crt += "\n"
        }
        val spritePos = (regVal - 1)..(regVal + 1)

        crt += if ((c % 40) in spritePos) {
            "#"
        } else {
            "."
        }
    }

    return crt;
}


private fun part1(lines: List<String>): Int {
    var curValue = 1
    val cycles = mutableListOf<Int>()
    for (line in lines) {
        val split = line.split(" ")
        val instruction = split[0]
        if (instruction == "noop") {
            cycles.add(curValue)
        } else {
            val addValue = split[1].toInt()
            cycles.add(curValue)
            cycles.add(curValue)
            curValue += addValue
        }
    }

    var result = 0
    for (i in 19..219 step 40) {
        val sigStrength = cycles[i] * (i + 1)
//        println("cycle ${i + 1} * ${cycles[i]} = $sigStrength")
        result += sigStrength
    }
    return result
}
