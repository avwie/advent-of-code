package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day1 : Day<Int, Int> {

    val readings = Input.inputLines(2021, 1).map { it.toInt() }.toList()

    override fun part1(): Int = countIncreasing(readings)

    override fun part2(): Int = readings
        .windowed(3)
        .map { it.sum() }
        .let(::countIncreasing)

    private fun countIncreasing(list: Iterable<Int>): Int = list
        .windowed(2)
        .filter { (a, b) -> b > a }
        .size
}