package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.mostCommonOrNull

object Day3 : Day<Int, Int> {

    private val lines = Input.inputLines(2021, 3).toList()

    override fun part1(): Int = gamma(lines) * epsilon(lines)
    override fun part2(): Int = oxygen(lines) * CO2(lines)

    private fun gamma(report: Iterable<String>) = mostCommonBits(report).toInt(2)
    private fun epsilon(report: Iterable<String>) = invert(mostCommonBits(report)).toInt(2)
    private fun oxygen(report: Iterable<String>) = filterByKey(report, '1').toInt(2)
    private fun CO2(report: Iterable<String>) = filterByKey(report, '0').toInt(2)

    fun filterByKey(report: Iterable<String>, key: Char): String {
        var remaining = report

        (0 until remaining.first().length).forEach { bit ->
            if (remaining.count() == 1) return@forEach

            val keyed = remaining.count { it[bit] == '1' }
            if (keyed >= remaining.count() - keyed) {
                remaining = remaining.filter { it[bit] == key }
            } else {
                remaining = remaining.filter { it[bit] != key }
            }
        }
        return remaining.first()
    }

    private fun mostCommonBits(lines: Iterable<String>): String = (0 until lines.first().length)
        .map { index ->
            lines
                .map { line -> line[index].toString() }
                .mostCommonOrNull()
        }
        .joinToString("")

    private fun invert(bits: String): String = bits
        .map { c -> if (c == '0') '1' else '0' }
        .joinToString("")
}