package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.*

object Day25 : Day<Long, Unit> {

    private val PATTERN = Regex("\\D*(\\d{4})\\D*(\\d{4}).")
    private val input = Input.inputLinesRegex(2015, 25, PATTERN).first().drop(1)
    val row = input[0].toInt()
    val col = input[1].toInt()

    override fun part1(): Long  = codes(20151125, 252533, 33554393)
        .drop(rowColToIndex(row, col) - 1)
        .first()

    override fun part2() {}

    fun rowColToIndex(row: Int, col: Int): Int {
        val colStartValue = (1 until row).fold(1) { acc, n -> acc + n }
        return (1 until col).fold(colStartValue) { acc, n -> acc + n + row }
    }

    fun codes(init: Long, multiplier: Long, divider: Long) = sequence {
        var last = init
        while (true) {
            yield(last)
            last = (last * multiplier) % divider
        }
    }
}