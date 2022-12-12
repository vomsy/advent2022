fun main() {
    val sample1 = part1(read2DimArrayStr("day12/sample.txt"))
    want(sample1, 31)
    val part1 = part1(read2DimArrayStr("day12/input.txt"))
    println("Part 1: $part1")
    want(part1, 0)

    val sample2 = part2(read2DimArrayStr("day12/sample.txt"))
    want(sample2, 0)
    val part2 = part2(read2DimArrayStr("day12/input.txt"))
    println("Part 2: $part2")
    want(part2, 0)
}

private fun part1(arr: Array<Array<Char>>): Int {
    var start = Pos(0, 0)
    var end = Pos(0, 0)
    for ((r, row) in arr.withIndex()) {
        for ((c, value) in row.withIndex()) {
            if (value == 'S') {
                start = Pos(r, c)
            } else if (value == 'E') {
                end = Pos(r, c)
            }
        }
    }
    println(start)
    println(end)

    return arr.findPath(start, end).size
}

private fun Array<Array<Char>>.findPath(from: Pos, to: Pos): List<Pos> {
    val q = mutableListOf(from);
    val visited = hashSetOf<Pos>(from)
    val prev = mutableMapOf<Pos, Pos>()

    while (q.isNotEmpty()) {
        val first = q.removeFirst()

        for (n in this.neighbours(first.row, first.col)) {
           if (!visited.contains(n)) {
               visited.add(n)
               prev[n] = first
               if (n == to) {
                  q.clear()
               } else {
                   q.add(n)
               }
           }
        }
    }

    val path = mutableListOf<Pos>();
    var curr = to
    while(curr != from) {
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
    ).filter { it.row >= 0 && it.col >= 0 && it.row < this.size && it.col < this[0].size }

    var fromValue = this[row][col]

    if (fromValue == 'S') {
        fromValue = 'a'
    }
    return ways
        .filter { p ->
            var pValue = this[p.row][p.col]

            if (pValue == 'E') {
                pValue = 'z'
            }

            (pValue.code - fromValue.code) <= 1
        }
}

data class Pos(val row: Int, val col: Int)

private fun part2(arr: Array<Array<Char>>): Int {
    TODO("Not yet implemented")
}
