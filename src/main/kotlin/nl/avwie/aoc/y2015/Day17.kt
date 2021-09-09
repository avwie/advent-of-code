package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day17 : Day<Int, Int> {

    override fun part1(): Int = combinations(150, Input.inputLines(2015, 17).map { it.toInt() }.toList()).size

    override fun part2(): Int = combinations(150, Input.inputLines(2015, 17).map { it.toInt() }.toList())
        .groupBy { it.size }
        .minByOrNull { it.key }!!
        .value.size

    fun combinations(amount: Int, containers: List<Int>, acc: List<Int> = listOf()): List<List<Int>> = when {
        amount < 0 -> listOf()
        amount == 0 -> listOf(acc)
        else -> containers.indices.flatMap { index ->
            combinations(amount - containers[index], containers.drop(index + 1), acc + containers[index])
        }.filter { it.isNotEmpty() }
    }
}