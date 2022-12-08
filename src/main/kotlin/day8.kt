fun main() {
    val sample2 = part2(read2DimArray("day8/sample.txt"))
    want(sample2, 8)
    val part2 = part2(read2DimArray("day8/input.txt"))
    println("Part 2: $part2")
    want(part2, 252000)
}

private fun part2(grid: Array<IntArray>): Int {
    return grid
        .map2DIndexed { r, c, _ -> grid.scenicScore(r, c) }
        .max()
}

fun <T> Array<IntArray>.map2DIndexed(func: (Int, Int, Int) -> T): List<T> {
    return this.flatMapIndexed { r, row ->
        row.mapIndexed { c, value -> func(r, c, value) }
    }
}

fun <T> Array<IntArray>.map2D(func: (Int) -> T): List<T> {
    return this.map2DIndexed { _, _, value -> func(value) }
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


