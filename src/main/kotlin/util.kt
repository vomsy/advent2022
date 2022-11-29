import java.io.File

fun readFileLines(fileName: String): List<String> {
  return File("src/main/resources", fileName).readLines()
}
