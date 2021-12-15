package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*

object Day3 : Day<Int, Int> {

    override fun part1(): Int = Input.inputLines(2016, 3).count { isValid(parse(it)) }

    override fun part2(): Int = Input.inputLines(2016, 3).map(::parse)
        .windowed(3, 3)
        .map { group -> (0 .. 2).map { c -> group.map { it[c]!! } } }
        .sumOf { group ->
            group.count { isValid(it) }
        }

    fun parse(input: String): List<Int> = input.windowed(5, step = 5).map { it.trim().toInt() }
    fun isValid(edges: List<Int>) = edges.sorted().let { (a, b, c) -> a + b > c }
}