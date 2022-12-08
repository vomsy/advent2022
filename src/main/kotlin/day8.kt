fun main() {
    val sample2 = part2(read2DimArray("day8/sample.txt"))
    want(sample2, 8)
    val part2 = part2(read2DimArray("day8/input.txt"))
    println("Part 2: $part2")
    want(part2, 252000)
}

private fun part2(grid: Array<IntArray>): Int {
    var max = 0
    for ((r, row) in grid.withIndex()) {
        for (c in row.indices) {
            val newMax = grid.scenicScore(r, c)
            if (newMax > max) {
                max = newMax
            }
        }
    }
    return max
}

fun Array<IntArray>.scenicScore(r: Int, c: Int): Int {
    val value = this[r][c]
    return this.count(r, c, value, { it }, { it + 1 }) *
            this.count(r, c, value, { it }, { it - 1 }) *
            this.count(r, c, value, { it - 1 }, { it }) *
            this.count(r, c, value, { it + 1 }, { it })
}

fun Array<IntArray>.count(r: Int, c: Int, value: Int, rowFun: (Int) -> Int, colFun: (Int) -> Int): Int {
    val nextR = rowFun(r)
    val nextC = colFun(c)

    if (this.getOrNull(nextR) == null || this[nextR].getOrNull(nextC) == null) {
        return 0
    }

    if (this[nextR][nextC] >= value) {
        return 1
    }

    return 1 + this.count(nextR, nextC, value, rowFun, colFun)
}


