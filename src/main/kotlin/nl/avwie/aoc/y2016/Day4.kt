package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*

object Day4 : Day<Int, Int> {

    private val REGEX = Regex("(\\D*)(\\d*)\\[(\\D*)\\]")

    override fun part1(): Int = Input.inputLinesRegex(2016, 4, REGEX).sumOf { (_, name, id, checksum) ->
        if (checksum == checksum(name)) id.toInt() else 0
    }

    override fun part2(): Int = Input.inputLinesRegex(2016, 4, REGEX)
        .filter { (_, name, _, checksum) -> checksum(name) == checksum }
        .map { (_, name, id, _) -> rotate(name).take(id.toInt() % 26).last() to id.toInt() }
        .first { it.first.contains("north") }.second

    fun rotate(input: String): Sequence<String> {
        val cypher = ('a' .. 'z').let { chars -> chars.zip(chars.drop(1) + chars.first()).toMap() }
        return generateSequence(input) { curr ->
            curr.map { c -> cypher[c] ?: ' ' }.joinToString("")
        }.drop(1)
    }

    fun checksum(input: String): String {
        val groups = input.filter { it.isLetter() }.groupBy { it }.map { (k, v) -> k to v.size }
        return groups.sortedBy { it.first }.sortedByDescending { it.second }.take(5).joinToString("") { it.first.toString() }
    }
}