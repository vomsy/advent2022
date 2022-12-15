import Direction.*
import kotlin.math.abs

fun main() {
    val sample1 = part1(readFileLines("day9/sample.txt"))
    want(sample1, 13)
    val part1 = part1(readFileLines("day9/input.txt"))
    println("Part 1: $part1")
    want(part1, 6745)

    val sample2 = part2(readFileLines("day9/sample.txt"))
    want(sample2, 1)
    val larger = part2(readFileLines("day9/larger.txt"))
    want(larger, 36)
    val part2 = part2(readFileLines("day9/input.txt"))
    println("Part 2: $part2")
    want(part2, 2793)
}

private data class Point(val x: Int, val y: Int) {
    fun move(d: Direction, n: Int): Point = when (d) {
        R -> Point(x + n, y)
        L -> Point(x - n, y)
        U -> Point(x, y + n)
        D -> Point(x, y - n)
    }

    override fun toString(): String = "($x,$y)"

    fun closeTo(other: Point): Boolean {
        val xdiff = abs(this.x - other.x)
        val ydiff = abs(this.y - other.y)
        return xdiff in 0..1 && ydiff in 0..1
    }


    fun isDiagonal(point: Point): Boolean {
        return this.x != point.x && this.y != point.y
    }
}

enum class Direction {
    R, L, U, D
}

private fun part1(lines: List<String>): Int {
    val tailVisited = mutableSetOf<Point>()
    var head = Point(0, 0)
    var tail = head
    tailVisited.add(tail)
    for (line in lines) {
        val split = line.split(" ")
        val direction = Direction.valueOf(split[0])
        val count = split[1].toInt()

        val newHead = head.move(direction, count)
        val headPath = path(head, newHead);

        for ((i, p) in headPath.withIndex()) {
            if (!p.closeTo(tail)) {
                tail = headPath[i - 1]
                tailVisited.add(tail)
            }
        }
        head = newHead
    }
    return tailVisited.size
}

private fun path(p1: Point, p2: Point): List<Point> {
    val points = mutableListOf<Point>()
    if (p1.x == p2.x) {
        val rng = if (p2.y < p1.y) {
            p1.y downTo p2.y
        } else {
            p1.y..p2.y
        }
        for (y in rng) {
            points.add(Point(p1.x, y))
        }
    } else if (p1.y == p2.y) {
        val rng = if (p2.x < p1.x) {
            p1.x downTo p2.x
        } else {
            p1.x..p2.x
        }
        for (x in rng) {
            points.add(Point(x, p1.y))
        }
    }
    return points
}

private fun part2(lines: List<String>): Int {
    val tailVisited = mutableSetOf<Point>()
    var head = Point(0, 0)
    var tails = Array(9) { head }
    tailVisited.add(tails.last())
    for (line in lines) {
        val split = line.split(" ")
        val direction = Direction.valueOf(split[0])
        val count = split[1].toInt()

        val newHead = head.move(direction, count)
        val headPath = path(head, newHead);

        for ((i, h) in headPath.withIndex()) {
            val before = listOf(*tails)
            if (!h.closeTo(tails[0])) {
                tails[0] = headPath[i - 1]
                for (t in 1..8) {
                    val thisHead = tails[t - 1]
                    val headBefore = before[t - 1]
                    val thisTail = tails[t]

                    if (!thisTail.closeTo(thisHead)) {
                        if (thisTail.x == thisHead.x) {
                            tails[t] = Point(
                                thisTail.x, if (thisTail.y < thisHead.y) {
                                    thisTail.y + 1
                                } else {
                                    thisTail.y - 1
                                }
                            )
                        } else if (thisTail.y == thisHead.y) {
                            tails[t] = Point(
                                if (thisTail.x < thisHead.x) {
                                    thisTail.x + 1
                                } else {
                                    thisTail.x - 1
                                }, thisTail.y
                            )
                        } else if (headBefore.isDiagonal(thisHead)) {
                            if (headBefore.x < thisHead.x && headBefore.y < thisHead.y) {
                                tails[t] = Point(thisTail.x + 1, thisTail.y + 1)
                            } else if (headBefore.x < thisHead.x
                                && headBefore.y > thisHead.y
                            ) {
                                tails[t] = Point(thisTail.x + 1, thisTail.y - 1)
                            } else if (headBefore.x > thisHead.x
                                && headBefore.y > thisHead.y
                            ) {
                                tails[t] = Point(thisTail.x - 1, thisTail.y - 1)
                            } else {
                                // problem, if it jumps diag, it odesn't need to jump if its on the same axis
                                tails[t] = Point(thisTail.x - 1, thisTail.y + 1)
                            }
                        } else {
                            tails[t] = headBefore
                        }
                    }
                }

                tailVisited.add(tails.last())
            }
        }
        head = newHead
        println("$line: H:${head}; T:${tails.reversed()}")
    }
    return tailVisited.size
}
