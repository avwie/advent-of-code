package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import kotlin.math.abs

object Day7 : Day<Int, Int> {

    val input = parse(Input.input(2021, 7))

    override fun part1(): Int = (0 .. input.maxOrNull()!!).minOf { linearCost(input, it) }
    override fun part2(): Int = (0 .. input.maxOrNull()!!).minOf { polynomialCost(input, it) }

    fun parse(input: String): List<Int> = input.split(",").map { it.toInt() }

    fun linearCost(crabs: List<Int>, position: Int): Int = crabs.sumOf { crab -> abs(crab - position) }
    fun polynomialCost(crabs: List<Int>, position: Int) = crabs.sumOf { crab -> abs(crab - position).let { ds -> ds * (ds + 1) / 2 } }
}