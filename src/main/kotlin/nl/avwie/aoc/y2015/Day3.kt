package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Complex
import nl.avwie.aoc.common.Complex.Companion.j
import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day3 : Day<Int, Int> {

    val INPUT = Input.input(2015, 3).toCharArray()

    override fun part1(): Int = visits(INPUT.asSequence()).groupBy { it }.size

    override fun part2(): Int = (visits(INPUT.asSequence().filterIndexed { i, _ -> i % 2 == 0} ) + visits(INPUT.asSequence().filterIndexed { i, _ -> i % 2 != 0} )).groupBy { it }.size

    private fun visits(sequence: Sequence<Char>): Sequence<Complex> = sequence
        .scan(Complex.ZERO) { pos, char ->
            when (char) {
                '^' -> pos - j
                '>' -> pos + 1
                '<' -> pos - 1
                'v' -> pos + j
                else -> pos
            }
        }
}