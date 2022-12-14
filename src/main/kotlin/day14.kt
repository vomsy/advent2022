import kotlin.math.max
import kotlin.math.min

fun main() {
    val sample = part2(readFileLines("day14/sample.txt"))
    want(sample, 93)
    val part2 = part2(readFileLines("day14/input.txt"))
    println("Part 2: $part2")
    want(part2, 27551)
}

private fun part2(lines: List<String>): Int {
    val start = Elem(500, 0)
    val rocks = mutableSetOf<Elem>()
    val sand = mutableSetOf<Elem>()
    addRocks(lines, rocks)
    val floor = rocks.maxBy { it.y }.y + 2

    while (true) {
        val nextSand = findNextSpot(start, rocks, floor)
        rocks.add(nextSand)
        sand.add(nextSand)
        if (nextSand == start) {
            break
        }
    }

//    plot(rocks, sand)
    return sand.size
}

private fun plot(rocks: Set<Elem>, sand: Set<Elem>) {
    println()
    val (l, r) = findEdgeRocks(rocks)
    for (y in 0..rocks.maxBy { it.y }.y) {
        for (x in l.x..r.x) {
            if (sand.contains(Elem(x, y))) {
                print("o")
            } else if (rocks.contains(Elem(x, y))) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
    println()
}

private tailrec fun findNextSpot(curr: Elem, rocks: Set<Elem>, floor: Int): Elem {

    val down = Elem(curr.x, curr.y + 1)
    val left = Elem(curr.x - 1, down.y)
    val right = Elem(curr.x + 1, down.y)

    if (down.y == floor) {
       return curr
    }

    if (!rocks.contains(down)) {
        return findNextSpot(down, rocks, floor)
    }

    return if (!rocks.contains(left)) {
        findNextSpot(left, rocks, floor)
    } else if (!rocks.contains(right)) {
        findNextSpot(right, rocks, floor)
    } else {
        curr
    }
}

private fun findEdgeRocks(points: Set<Elem>): List<Elem> {
    return listOf(
        points.minBy { it.x },
        points.maxBy { it.x }
    )
}

private fun addRocks(lines: List<String>, points: MutableSet<Elem>) {
    for (line in lines) {
        val split = line.split(" -> ")
        for (i in 1 until split.size) {
            val prev = split[i - 1].split(",").map { it.toInt() }
            val curr = split[i].split(",").map { it.toInt() }

            for (x in min(prev[0], curr[0])..max(prev[0], curr[0])) {
                for (y in min(prev[1], curr[1])..max(prev[1], curr[1])) {
                    points.add(Elem(x, y))
                }
            }
        }
    }
}


private data class Elem(val x: Int, val y: Int)
