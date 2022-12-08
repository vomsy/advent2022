fun main() {
    val sample2 = part2(read2DimArray("day8/sample.txt"))
    check(sample2 == 8) { println(sample2) }
    val part2 = part2(read2DimArray("day8/input.txt"))
    println("Part 2: $part2")
    check(part2 == 252000)
}

private fun part2(grid: Array<IntArray>): Int {
    var max = 0
    for ((r, row) in grid.withIndex()) {
        for ((c, currVal) in row.withIndex()) {
            val countLeft = countHorizontal(grid, c, currVal, (r-1) downTo 0)
            val countRight = countHorizontal(grid, c, currVal, (r+1)..grid.lastIndex)
            val countUp = countVertical(grid, r, currVal, (c-1) downTo 0)
            val countDown = countVertical(grid, r, currVal, (c+1)..row.lastIndex)

            val newMax = countLeft * countRight * countDown * countUp
            if (newMax > max) {
                max = newMax
            }
        }
    }
    return max
}




fun countHorizontal(grid: Array<IntArray>, colIdx: Int, currValue: Int, intRange: IntProgression): Int {
    var count = 0
    for (i in intRange) {
        count++
        if (grid[i][colIdx] >= currValue) {
            break
        }
    }
    return count
}

fun countVertical(grid: Array<IntArray>, rowIdx: Int, currValue: Int, intRange: IntProgression): Int {
    var count = 0
    for (i in intRange) {
        count++
       if (grid[rowIdx][i] >= currValue) {
           break
       }
    }
    return count
}
