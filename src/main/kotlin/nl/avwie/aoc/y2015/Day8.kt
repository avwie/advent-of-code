package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day8 : Day<Int, Int> {

    override fun part1(): Int = Input.inputLines(2015, 8)
        .map { input -> input.length - unescape(input).length }
        .sum()

    override fun part2(): Int = Input.inputLines(2015, 8)
        .map { input -> encode(input).length - input.length }
        .sum()

    fun unescape(input: String): String = input
        .slice(1 until input.length - 1)
        .replace("\\\\x[a-f0-9]{2}".toRegex(), ".")
        .replace("\\\"", "\"")
        .replace("\\\\","\\")

    fun encode(input: String): String = input
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .let { "\"$it\"" }
}