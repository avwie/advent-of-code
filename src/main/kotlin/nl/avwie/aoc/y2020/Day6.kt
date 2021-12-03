package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day6 : Day<Long, Long> {

    override fun part1(): Long = Input.input(2020, 6)
        .let(::groups)
        .map(::union)
        .sum()

    override fun part2(): Long = Input.input(2020, 6)
        .let(::groups)
        .map(::intersect)
        .sum()

    fun groups(input: String): List<List<Set<Char>>> = input
        .split("\n\n")
        .map { it.split("\n").map { it.toSet() } }

    fun union(groups: List<Set<Char>>): Long = groups
        .reduce { a, b -> a union b }.size.toLong()

    fun intersect(groups: List<Set<Char>>): Long = groups
        .reduce { a, b -> a intersect b }.size.toLong()
}