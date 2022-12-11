fun main() {
    val sample1 = part1(readFileSplitBy("day11/sample.txt", "\n\n"))
    want(sample1, 10605)
    val part1 = part1(readFileSplitBy("day11/input.txt", "\n\n"))
    println("Part 1: $part1")
    want(part1, 120756)
}

private fun part1(monkeyInput: List<String>): Long {
    val monkeys = mutableListOf<Monkey>()
    for (m in monkeyInput) {
        val monkey = parseMonkey(m)
        monkeys.add(monkey)
    }

    for (r in 1..20) {
        for ((i, m) in monkeys.withIndex()) {
            while (m.items.isNotEmpty()) {
                val item = m.pop()!!
                monkeys[item.mIdx].add(item.worry)
            }
        }
    }
    val sortedDescending = monkeys.map { it.inspected() }.sortedDescending()
    return sortedDescending.take(2).reduce { i1, i2 -> i1 * i2 }.toLong()
}

fun parseMonkey(m: String): Monkey {
    val split = m.split("\n")
    val items: List<Long> = split[1]
        .filter { it.isDigit() || it == ',' }
        .split(",")
        .map { it.toLong() }

    val opStr = split[2].split("=")[1].split(" ")
    val sign = opStr[2]
    val arg = opStr[3]
    val op = { old: Long ->
        val operand = when (arg) {
            "old" -> old
            else -> arg.toLong()
        }

        when (sign) {
            "+" -> old + operand
            "-" -> old - operand
            "*" -> old * operand
            "/" -> old / operand
            else -> throw IllegalStateException()
        }
    }

    return Monkey(
        items = items.toMutableList(),
        op = op,
        test = split[3].filter { it.isDigit() }.toInt(),
        m1 = split[4].filter { it.isDigit() }.toInt(),
        m2 = split[5].filter { it.isDigit() }.toInt()
    )
}

data class Monkey(
    val items: MutableList<Long>,
    val op: (Long) -> Long,
    val test: Int,
    val m1: Int,
    val m2: Int
) {
    private var inspected: Int = 0
    fun inspected(): Int = this.inspected

    fun add(item: Long) {
        items.add(item)
    }

    fun pop(): Item? {
        if (items.isEmpty()) {
            return null
        }
        val item = items.removeFirst()
        this.inspected++
        val worry: Long = op(item) / 3
        return if (worry % test == 0L) {
            Item(worry, m1)
        } else {
            Item(worry, m2)
        }
    }

    override fun toString(): String = "items=$items, mod $test ? $m1 : $m2"
}


data class Item(val worry: Long, val mIdx: Int)
