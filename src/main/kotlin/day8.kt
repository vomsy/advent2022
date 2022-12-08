fun main() {
//    val sample1 = part1(read2DimArray("day8/sample.txt"))
//    check(sample1 == 21) { println(sample1) }
//    val part1 = part1(read2DimArray("day8/input.txt"))
//    println("Part 1: $part1")

    val sample2 = part2(read2DimArray("day8/sample.txt"))
    check(sample2 == 8) { println(sample2) }
    val part2 = part2(read2DimArray("day8/input.txt"))
    println("Part 2: $part2")
}

private fun part2(grid: Array<IntArray>): Int {
    var max = 0
    for ((r, row) in grid.withIndex()) {
        for ((c, curr) in row.withIndex()) {
            //go left
            var countLeft = 0
            for (i in (r - 1) downTo 0) {
                countLeft++
                if (grid[i][c] >= curr) {
                    break
                }
            }

            var countRight = 0
            for (i in (r + 1)..grid.lastIndex) {
                countRight++
                if (grid[i][c] >= curr) {
                    break;
                }
            }

            var countUp = 0
            for (i in (c - 1) downTo 0) {
                countUp++
                if (grid[r][i] >= curr) {
                    break
                }
            }

            //go down
            var countDown = 0
            for (i in (c + 1)..row.lastIndex) {
                countDown++
                if (grid[r][i] >= curr) {
                    break
                }
            }
            val newMax = countLeft * countRight * countDown * countUp
            if (newMax > max) {
                max = newMax
            }
        }
    }
    return max
}
