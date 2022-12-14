import kotlin.math.max
import kotlin.math.min

fun main() {
    val sample = part1(readFileLines("day14/sample.txt"))
    want(sample, 24)
    val part1 = part1(readFileLines("day14/input.txt"))
    println("Part 1: $part1")
    want(part1, 745)
}

private fun part1(lines: List<String>): Int {
    val start = Elem(500, 0)
    val rocks = mutableSetOf<Elem>()
    val sand = mutableSetOf<Elem>()
    addRocks(lines, rocks)

    while (true) {
        val nextSand = findNextSpot(start, rocks) ?: break
        rocks.add(nextSand)
        sand.add(nextSand)
    }

    plot(rocks, sand)
    return sand.size
}

private fun plot(rocks: Set<Elem>, sand: Set<Elem>) {
    println()
    val (l, r) = findEdgeRocks(rocks)
    for(y in 0..rocks.maxBy { it.y }.y) {
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

private tailrec fun findNextSpot(curr: Elem, rocks: Set<Elem>): Elem? {
    val (leftEdge, rightEdge) = findEdgeRocks(rocks)
    val down = Elem(curr.x, curr.y + 1)
    val left = Elem(curr.x - 1, down.y)
    val right = Elem(curr.x + 1, down.y)

    if (!rocks.contains(down)) {
        return findNextSpot(down, rocks)
    }

    if (!rocks.contains(left)) {
        if (down == leftEdge) {
            return null
        }
        return findNextSpot(left, rocks)
    } else if (!rocks.contains(right)) {
        if (down == rightEdge) {
            return null
        }
        return findNextSpot(right, rocks)
    } else {
        return curr
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
