package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day1 : Day<Long, Long> {

    val calories = Input.input(2022, 1).parse()

    fun String.parse() =
        split("\n\n")
        .map { group ->
            group
                .lines()
                .map { it.toLong(10)
                }
        }

    override fun part1(): Long = calories.maxOfOrNull { it.sum() }!!

    override fun part2(): Long = calories
        .map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}