package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.*

object Day24 : Day<Long, Int> {

    private val input = Input.inputLines(2015, 24).map { it.toInt() }.toList()

    override fun part1(): Long = smallestGroupWithLargestQED(input, input.sum() / 3).QE()

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun smallestGroupWithLargestQED(input: List<Int>, target: Int): List<Int> = groups(input, target)
        .groupBy { it.size }
        .minByOrNull { it.key }!!.value
        .maxByOrNull { it.QE() }!!

    fun List<Int>.QE(): Long = this.map { it.toLong() }.reduce { a, b ->  a * b }

    fun groups(options: List<Int>, target: Int): List<List<Int>> {

        fun inner(remainingOptions: List<Int>, remaining: Int, acc: List<List<Int>>): List<List<Int>> = when {
            remaining == 0 -> acc
            remaining < 0 -> listOf()
            remainingOptions.none { it <= remaining } -> listOf()
            else -> remainingOptions.indices.flatMap { index ->
                val option = remainingOptions[index]
                val newRemaining = remaining - option
                val newRemainingOptions = remainingOptions.drop(index + 1).filter { it <= newRemaining }
                val newAcc = acc.map { it + option }
                inner(newRemainingOptions, newRemaining, newAcc)
            }
        }

        return inner(options.sortedBy { -it }, target, listOf(listOf()))
    }
}