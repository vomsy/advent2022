import Move.*

enum class Move(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun winsFrom(): Move = Move.values().getOrElse(this.ordinal - 1) { SCISSORS }
    fun losesFrom(): Move = Move.values().getOrElse(this.ordinal + 1) { ROCK }
    fun gameScore(enemy: Move): Int = if (this == enemy) 3 else if (this.winsFrom() == enemy) 6 else 0
    fun finalScore(enemy: Move): Int = this.score + this.gameScore(enemy)
}

val moves: Map<String, Move> = mapOf(
    "A" to ROCK, "X" to ROCK,
    "B" to PAPER, "Y" to PAPER,
    "C" to SCISSORS, "Z" to SCISSORS
)

private fun part1(lines: List<String>): Int {
    return lines
        .map { it.split(" ") }
        .sumOf {
            val enemy: Move = moves.getValue(it[0])
            val me: Move = moves.getValue(it[1])
            me.finalScore(enemy)
        }
}

private fun part2(lines: List<String>): Int {
    return lines
        .map { it.split(" ") }
        .sumOf {
            val enemy: Move = moves.getValue(it[0])
            val me: Move = when (it[1]) {
                "X" -> enemy.winsFrom()
                "Y" -> enemy
                else -> enemy.losesFrom()
            }
            me.finalScore(enemy)
        }
}

fun main() {
    val part1Sample = part1(readFileLines("day2/sample.txt"))
    check(part1Sample == 15)
    val part1 = part1(readFileLines("day2/input.txt"))
    println("Part 1: $part1")
    check(part1 == 12276)

    check(part2(readFileLines("day2/sample.txt")) == 12)
    val part2 = part2(readFileLines("day2/input.txt"))
    println("Part 2: $part2")
    check(part2 == 9975)
}
