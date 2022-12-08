import java.io.File

fun readFileLines(fileName: String): List<String> {
    return File("src/main/resources", fileName).readLines()
}

fun readFileAsStr(fileName: String): String {
    return File("src/main/resources", fileName).readText()
}

fun read2DimArray(fileName: String): Array<IntArray> {
    val lines = readFileLines(fileName)
    val rows = lines.size
    val cols = lines[0].length
    val array = Array(rows) { IntArray(cols) }

    for ((row, line) in lines.withIndex()) {
        for ((col, n) in line.withIndex()) {
            array[row][col] = n.digitToInt()
        }
    }

    return array
}

fun <T> want(value: T, want: T) {
    check(value == want) { println("Wanted $want, Got $value") }
}
