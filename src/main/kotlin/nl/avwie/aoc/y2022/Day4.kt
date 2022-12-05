package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day4 : Day<Int, Int> {

    val pairs = Input.inputLines(2022, 4).parse()

    override fun part1(): Int = pairs
        .filter { (a, b) -> hasFullOverlap(a, b) }
        .count()

    override fun part2(): Int = pairs
        .filter { (a, b) -> hasAnyOverlap(a, b) }
        .count()

    private fun Sequence<String>.parse() =
        map { line -> line.split(',').map(::set) }.toList()

    private fun set(input: String) = input
        .split('-')
        .map { it.toInt(10) }
        .let { (from, to) -> from .. to }
        .toSet()

    private fun hasFullOverlap(a: Set<Int>, b: Set<Int>) = listOf(a.size, b.size).contains(a.intersect(b).size)

    private fun hasAnyOverlap(a: Set<Int>, b: Set<Int>) = a.intersect(b).isNotEmpty()
}