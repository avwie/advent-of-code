package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day

object Day10 : Day<Int, Int> {

    val INPUT = "3113322113"

    override fun part1(): Int = generateSequence(INPUT, ::nextSequence).drop(1).take(40).last().length

    override fun part2(): Int = generateSequence(INPUT, ::nextSequence).drop(1).take(50).last().length

    fun splitByNumber(input: String): List<Pair<Char, Int>> = input.fold(mutableListOf()) { acc, char ->
        when {
            acc.isEmpty() -> acc.add(char to 1)
            acc.last().first == char -> acc.last().also { (c, i) -> acc[acc.size - 1] = c to (i + 1) }
            else -> acc.add(char to 1)
        }
        acc
    }

    fun nextSequence(input: String): String = splitByNumber(input)
        .joinToString("") { (char, count) ->  "$count$char"}
}