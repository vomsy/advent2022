import kotlin.math.abs

fun main() {
    val sample = part2(readFileLines("day15/sample.txt"), 20)
    want(sample, 56000011)
    val part2 = part2(readFileLines("day15/input.txt"), 4_000_000)
    println("Part 2: $part2")
    want(part2, 13172087230812)
}

private fun part2(lines: List<String>, maxNum: Long): Long {
    val sigAndBeacons = mutableListOf<List<P>>()
    for (line in lines) {
        val (sX, sY, bX, bY) = parse(line)
        val sig = P(sX, sY)
        val beacon = P(bX, bY)
        sigAndBeacons.add(listOf(sig, beacon))
    }

    val outerPoints: Set<P> = sigAndBeacons
        .flatMap { findOuterPoints(it) }
        .filter { it.insideGrid(maxNum) }.toSet()

    var r = P(0, 0)
    for (p in outerPoints) {
        var insideAny = false

        for ((s, b) in sigAndBeacons) {
            if (p == s || p == b) {
                insideAny = true
                break
            }
            if (s.manhattan(p) <= s.manhattan(b)) {
                insideAny = true
                break
            }
        }

        if (!insideAny) {
            r = p
            break
        }
    }

    println("Found beacon: $r")
    return r.x * 4_000_000 + r.y
}

private fun findOuterPoints(signalAndBeacon: List<P>): Set<P> {
    val (sig, b) = signalAndBeacon
    val m = sig.manhattan(b) + 1
    val outerEdgePoints = mutableSetOf<P>()

    var count = 0
    for (x in (sig.x - m)..sig.x) {
        outerEdgePoints.add(P(x, sig.y + count))
        outerEdgePoints.add(P(x, sig.y - count))
        count++
    }

    count = 0
    for (x in (sig.x + m) downTo sig.x) {
        outerEdgePoints.add(P(x, sig.y + count))
        outerEdgePoints.add(P(x, sig.y - count))
        count++
    }


    return outerEdgePoints
}

private fun parse(s: String): List<Long> {
    return "-?\\d+".toRegex().findAll(s)
        .map { it.value.toLong() }
        .toList()
}

private data class P(val x: Long, val y: Long) {
    fun manhattan(other: P): Long = abs(this.x - other.x) + abs(this.y - other.y)
    fun insideGrid(maxNum: Long): Boolean = this.x in 0..maxNum && this.y in 0..maxNum
}
