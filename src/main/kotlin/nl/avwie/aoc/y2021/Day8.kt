package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day8 : Day<Int, Long> {

    private val segments = listOf(
        setOf(0, 1, 2, 4, 5, 6),
        setOf(2, 5),
        setOf(0, 2, 3, 4, 6),
        setOf(0, 2, 3, 5, 6),
        setOf(1, 2, 3, 5),
        setOf(0, 1, 3, 5, 6),
        setOf(0, 1, 3, 4, 5, 6),
        setOf(0, 2, 5),
        setOf(0, 1, 2, 3, 4, 5, 6),
        setOf(0, 1, 2, 3, 5, 6)
    )

    private val numbersToSegments = segments.mapIndexed { nr, segments -> nr to segments }.toMap()
    private val segmentsToNumbers = segments.mapIndexed { nr, segments -> segments to nr }.toMap()

    private val entries = Input.inputLines(2021, 8).toList()

    override fun part1(): Int = entries
        .map { line -> line.split(" | ")[1] }
        .sumOf { output ->
            output.split(" ").count { setOf(2, 4, 3, 7).contains(it.length) }
        }

    override fun part2(): Long = entries.sumOf { decodeOutput(it, createCodex(it)) }

    private fun createCodex(entry: String): Map<Char, Int> {
        val patterns = entry.split(" | ")[0].split(" ")
        val segmentsOptions = (0 .. 6).map { ('a' .. 'g').toMutableSet() }

        // count the occurrences of enabled segments. Some can only appear a certain amount.
        val occurrences = ('a'..'g').associateBy { c -> patterns.count { it.contains(c) } }
        listOf(1 to 6, 4 to 4, 5 to 9).forEach { (option, occurrence) -> segmentsOptions[option].removeIf { c -> c != occurrences[occurrence] } }

        // uniques -> digit to segment count
        listOf(1 to 2, 4 to 4, 7 to 3, 8 to 7).forEach { (digit, count) ->
            val pattern = patterns.first { it.length == count }.toSet()
            val segments = numbersToSegments[digit]!!
            segments.map { segmentsOptions[it] }.forEach { options ->
                options.removeIf { !pattern.contains(it) }
            }
        }

        // prune it
        segmentsOptions.prune()
        return segmentsOptions.mapIndexed { digit, set -> set.first() to digit }.toMap()
    }

    private fun decodeOutput(entry: String, codex: Map<Char, Int>): Long {
        val patterns = entry.split(" | ")[1].split(" ")
        val digits = patterns.map { pattern ->
            segmentsToNumbers[pattern.map { codex[it]!! }.toSet()]!!
        }
        return digits.toLong()
    }
    private fun List<MutableSet<Char>>.prune() = this
        .groupBy { it }.keys.sortedBy { it.size }
        .dropLast(1).forEach { group ->
            this.filter { it != group }.forEach { other -> other.removeAll(group) }
        }
}