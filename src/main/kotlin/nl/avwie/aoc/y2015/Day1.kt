package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day1 : Day<Int, Int> {

    val INPUT = instructions(Input.input(2015, 1))

    override fun part1(): Int = floor(INPUT).last()

    override fun part2(): Int = floor(INPUT).withIndex().first { v -> v.value < 0 }.index

    fun instructions(input: String): Sequence<Char> = input.toCharArray().asSequence()

    fun floor(instructions: Sequence<Char>): Sequence<Int> = instructions.scan(0) { current, char ->
        when (char) {
            '(' -> current + 1
            ')' -> current -1
            else -> current
        }
    }
}