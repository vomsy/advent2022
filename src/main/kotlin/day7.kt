const val totalDiskSpace: Int = 70000000
const val requiredFreeSpace: Int = 30000000

fun main() {
    val sample1 = part1(readFileLines("day7/sample.txt"))
    check(sample1 == 95437) { println(sample1) }
    val part1 = part1(readFileLines("day7/input.txt"))
    println("Part 1: $part1")
    check(part1 == 1084134) { println(part1) }

    val sample2 = part2(readFileLines("day7/sample.txt"))
    check(sample2 == 24933642) { println(sample2) }
    val part2 = part2(readFileLines("day7/input.txt"))
    println("Part 2: $part2")
    check(part2 == 6183184) { println(part2) }
}

private fun part1(lines: List<String>): Int {
    val dirs = mutableListOf<Dir>()
    val root: Dir = buildFileSystem(dirs, lines)

    return dirs
        .map { it.size() }
        .filter { it <= 100000 }
        .sum()
}

private fun part2(lines: List<String>): Int {
    val dirs = mutableListOf<Dir>()
    val root: Dir = buildFileSystem(dirs, lines)

    val currTotalSize = root.size();
    val needToFreeUp = requiredFreeSpace - (totalDiskSpace - currTotalSize)
    return dirs
        .filter { it.size() > needToFreeUp }
        .minByOrNull { it.size() }!!
        .size()
}

private fun buildFileSystem(dirs: MutableList<Dir>, lines: List<String>): Dir {
    val root = Dir("/", null)
    dirs.add(root)
    var currDir: Dir = root

    for (line in lines.subList(1, lines.size)) {
        if (line.startsWith("$ cd")) {
            var target = line.split(" ")[2]
            currDir = if (target == "..") {
                currDir.parent!!
            } else (if (target == "/") {
                root
            } else {
                currDir.childs.find { it.name == target }!!
            }) as Dir
        }

        if (line.startsWith("dir")) {
            val dirName = line.split(" ")[1]
            val dir = Dir(dirName, currDir)
            dirs.add(dir)
            currDir.childs.add(dir)
        }

        if (line[0].isDigit()) {
            val split = line.split(" ")
            currDir.childs.add(File(split[1], split[0].toInt(), currDir))
        }
    }
    return root
}

interface Node {
    fun size(): Int
    val name: String
    val parent: Dir?
}

class File(
    override val name: String,
    private val size: Int,
    override val parent: Dir?
) : Node {
    override fun size(): Int = this.size

    override fun toString(): String = this.name
}

class Dir(override val name: String, override val parent: Dir?) : Node {
    var childs = mutableListOf<Node>()

    override fun size(): Int = childs.sumOf { it.size() }

    override fun toString(): String = "dir ${this.name}"
}
