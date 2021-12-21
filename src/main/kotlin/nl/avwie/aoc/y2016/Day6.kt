package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*

object Day6 : Day<String, String> {

    val input = Input.inputLines(2016, 6).map { it.toList() }.toList().transpose()

    override fun part1(): String = input.map { it.mostCommonOrNull()!! }.joinToString("")
    override fun part2(): String = input.map { it.leastCommonOrNull()!! }.joinToString("")
}