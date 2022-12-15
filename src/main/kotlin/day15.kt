import kotlin.math.abs

fun main() {
    val parse = parse("Sensor at x=3972136, y=2425195: closest beacon is at x=4263070, y=2991690")
    check(
        parse == listOf<Long>(
            3972136, 2425195, 4263070, 2991690
        )
    ) { println(parse) }
    val parse2 = parse("Sensor at x=-10, y=-20: closest beacon is at x=-30, y=-40")
    check(
        parse2 == listOf<Long>(
            -10, -20, -30, -40
        )
    ) { println(parse2) }

    val sample = part1(readFileLines("day15/sample.txt"), 10)
    want(sample, 26)
    val part1 = part1(readFileLines("day15/input.txt"), 2000000)
    println("Part 1: $part1")
    want(part1, 5112034)

}

private fun part1(lines: List<String>, y: Long): Int {
    val sigAndBeacons = mutableListOf<List<P>>()
    for (line in lines) {
        val (sX, sY, bX, bY) = parse(line)
        val sig = P(sX, sY)
        val beacon = P(bX, bY)
        sigAndBeacons.add(listOf(sig, beacon))
    }

    val (minX, maxX) = findMinMaxX(sigAndBeacons)
    var count = 0
    for (x in minX..maxX) {
        val p = P(x, y)
        for ((sig, beacon) in sigAndBeacons) {
            if (p == beacon || p == sig) break

            val sigToB = sig.manhattan(beacon)
            val sigToPoint = sig.manhattan(p)
            if (sigToPoint <= sigToB) {
                count++
                break
            }
        }
    }

    return count
}

private fun findMinMaxX(sigAndBeacons: MutableList<List<P>>): List<Long> {
    return listOf(sigAndBeacons
        .map {
            val (s, b) = it
            val manhattan = s.manhattan(b)
            s.x - manhattan
        }
        .min(),
        sigAndBeacons
            .map {
                val (s, b) = it
                val manhattan = s.manhattan(b)
                s.x + manhattan
            }
            .max())
}

private fun parse(s: String): List<Long> {
    return "-?\\d+".toRegex().findAll(s)
        .map { it.value.toLong() }
        .toList()
}

private data class P(val x: Long, val y: Long) {

    fun manhattan(other: P): Long = abs(this.x - other.x) + abs(this.y - other.y)
}
