package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day1 : Day<Long, Long> {

    override fun part1(): Long {
        val input = Input.inputLines(2020, 1)
            .map { str -> str.toLong() }
        val result = findTuplesThatSumTo(input, 2020L)
        requireNotNull(result)
        return result.first * result.second
    }

    override fun part2(): Long {
        val input = Input.inputLines(2020, 1)
            .map { str -> str.toLong() }
        val result = findTriplesThatSumTo(input, 2020L)
        requireNotNull(result)
        return result.first * result.second * result.third
    }

    fun findTuplesThatSumTo(input: Sequence<Long>, to: Long): Pair<Long, Long>? {
        val singles = mutableListOf<Long>()
        input.forEach { x ->
            val y = singles.firstOrNull { y -> x + y == to }
            if (y != null) {
                return x to y
            } else {
                singles.add(x)
            }
        }
        return null
    }

    fun findTriplesThatSumTo(input: Sequence<Long>, to: Long): Triple<Long, Long, Long>? {
        val singles = mutableListOf<Long>()
        val tuples = mutableListOf<Pair<Long, Long>>()
        input.forEach { x ->
            val t = tuples.firstOrNull { (y, z) -> x + y + z == to }
            if (t != null) {
                return Triple(t.first, t.second, x)
            } else {
                singles.forEach { y ->
                    tuples.add(x to y)
                }
                singles.add(x)
            }
        }
        return null
    }
}