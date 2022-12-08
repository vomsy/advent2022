const val VV = 1;
const val HV = 2;

fun main() {
    val sample1 = part1(read2DimArray("day8/sample.txt"))
    check(sample1 == 21) { println(sample1) }
    val part1 = part1(read2DimArray("day8/input.txt"))
    println("Part 1: $part1")

    val sample2 = part2(read2DimArray("day8/sample.txt"))
    check(sample2 == 8) { println(sample2) }
    val part2 = part2(read2DimArray("day8/input.txt"))
    println("Part 2: $part2")
}

private fun part1(grid: Array<IntArray>): Int {
    val visibility = Array(grid.size) { IntArray(grid[0].size) }
    for ((i, r) in grid.withIndex()) {
        checkRow(visibility[i], r)
    }

    for (i in 0..grid[0].lastIndex) {
        checkCol(visibility, grid, i)
    }

    var count = 0
    for (row in visibility) {
        for (num in row) {
            if (num != 0) {
                count++
            }
        }
    }
    return count
}

private fun part2(grid: Array<IntArray>): Int {
    return 0
}

fun checkRow(visibility: IntArray, gridRow: IntArray) {
    var max = gridRow[0]
    for ((idx, num) in gridRow.withIndex()) {
        if (idx == 0) {
            visibility[idx] = HV
            continue
        }
        if (num > max) {
            visibility[idx] = HV
            max = num
        }
    }

    max = gridRow.last()
    for (idx in gridRow.lastIndex downTo 0) {
        if (idx == gridRow.lastIndex) {
            visibility[idx] = HV
            continue
        }

        if (gridRow[idx] > max) {
            visibility[idx] = HV
            max = gridRow[idx]
        }
    }
}

fun checkCol(visibility: Array<IntArray>, gridRow: Array<IntArray>, col: Int) {
    var max = gridRow[0][col]
    for (r in 0..gridRow.lastIndex) {
        if (r == 0) {
            visibility[r][col] = visibility[r][col] or VV
            continue
        }
        if (gridRow[r][col] > max) {
            visibility[r][col] = visibility[r][col] or VV
            max = gridRow[r][col]
        }
    }

    max = gridRow[gridRow.lastIndex][col]
    for (r in gridRow.lastIndex downTo 0) {
        if (r == gridRow.lastIndex) {
            visibility[r][col] = visibility[r][col] or VV
            continue
        }
        if (gridRow[r][col] > max) {
            visibility[r][col] = visibility[r][col] or VV
            max = gridRow[r][col]
        }
    }
}

