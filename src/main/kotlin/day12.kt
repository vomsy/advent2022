fun main() {
    val sample2 = part2(read2DimArrayStr("day12/sample.txt"))
    want(sample2, 29)
    val part2 = part2(read2DimArrayStr("day12/input.txt"))
    println("Part 2: $part2")
    want(part2, 399)
}

private fun part2(arr: Array<Array<Char>>): Int {
    var end = Pos(0, 0)
    for ((r, row) in arr.withIndex()) {
        for ((c, value) in row.withIndex()) {
            if (value == 'E') {
                end = Pos(r, c)
            }
        }
    }

    return arr.findPath(end).size
}

private fun Array<Array<Char>>.findPath(from: Pos): List<Pos> {
    val q = mutableListOf(from);
    val visited = hashSetOf(from)
    val prev = mutableMapOf<Pos, Pos>()

    var end: Pos? = null
    while (q.isNotEmpty()) {
        val first = q.removeFirst()

        for (n in this.neighbours(first.row, first.col)) {
            if (!visited.contains(n)) {
                visited.add(n)
                prev[n] = first
                if (this[n.row][n.col] == 'a' ||
                    this[n.row][n.col] == 'S'
                ) {
                    end = n
                    q.clear()
                } else {
                    q.add(n)
                }
            }
        }
    }

    val path = mutableListOf<Pos>();
    var curr = end!!
    while (curr != from) {
        path.add(curr)
        curr = prev[curr]!!
    }
    return path;
}


private fun Array<Array<Char>>.neighbours(row: Int, col: Int): List<Pos> {
    val ways = listOf(
        Pos(row - 1, col),
        Pos(row + 1, col),
        Pos(row, col + 1),
        Pos(row, col - 1)
    ).filter {
        it.row >= 0 && it.col >= 0
                && it.row < this.size && it.col < this[0].size
    }
    var fromValue = this[row][col]

    if (fromValue == 'E') {
        fromValue = 'z'
    }
    return ways
        .filter { p ->
            var pValue = this[p.row][p.col]
            if (pValue == 'S') {
                pValue = 'a'
            }
            fromValue <= pValue || fromValue.code - pValue.code == 1
        }
}

data class Pos(val row: Int, val col: Int)
