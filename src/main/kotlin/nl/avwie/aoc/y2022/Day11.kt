package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day11 : Day<Long, Long> {

    override fun part1(): Long = evaluate(getMonkeys(), true, 20)
    override fun part2(): Long = evaluate(getMonkeys(), false, 10000)

    private val OPERATION_REGEX = Regex("new = (\\S*) ([\\+\\*]) (\\S*)")
    private fun getMonkeys() = Input.input(2022, 11).let(::parseInput)

    fun evaluate(monkeys: List<Monkey>, reduce: Boolean, rounds: Int): Long {
        repeat(rounds) {
            monkeys.forEach { monkey -> monkey.inspectAll(monkeys, reduce) }
        }
        return monkeys
            .map { it.inspections }
            .sortedDescending()
            .take(2)
            .reduce { a, b -> a * b}
    }

    fun parseInput(input: String): List<Monkey> = input
        .split("\n\n")
        .map(::parseMonkey)

    private fun parseMonkey(block: String): Monkey = block
        .lines()
        .drop(1)
        .map { it.split(": ")[1] }
        .let { lines ->
            val (test, ifTrue, ifFalse) = parseTest(lines.drop(2))
                Monkey(
                    items = parseItems(lines[0]),
                    operation = parseOperation(lines[1]),
                    test, ifTrue, ifFalse
                )
    }

    private fun parseItems(line: String) = line.split(", ").map { it.toLong() }.toMutableList()

    private fun parseOperation(line: String): (Long) -> Long {
        val (left, op, right) = OPERATION_REGEX.matchEntire(line)!!.destructured
        return { old ->
            when (op) {
                "*" -> (left.toLongOrNull() ?: old) * (right.toLongOrNull() ?: old)
                "+" -> (left.toLongOrNull() ?: old) + (right.toLongOrNull() ?: old)
                else -> old
            }
        }
    }

    private fun parseTest(lines: List<String>): Triple<Long, Int, Int> {
        val test = lines[0].split(" ").last().toLong()
        val ifTrue = lines[1].split(" ").last().toInt()
        val ifFalse = lines[2].split(" ").last().toInt()
        return Triple(test, ifTrue, ifFalse)
    }

    class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val test: Long,
        val ifTrue: Int,
        val ifFalse: Int,
        var inspections: Long = 0L
    ) {
        fun inspectAll(monkeys: List<Monkey>, reduce: Boolean) {
            val multiplier = monkeys.fold(1L) { acc, monkey -> acc * monkey.test }

            items.forEach { item ->
                val newValue = when {
                    reduce -> operation(item) / 3L
                    else -> operation(item) % multiplier
                }
                val target = if (newValue % test == 0L) monkeys[ifTrue] else monkeys[ifFalse]
                target.items.add(newValue)
                inspections += 1L
            }
            items.clear()
        }
    }
}