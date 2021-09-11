package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day

object Day20 : Day<Int, Int> {

    private val input = 34000000

    override fun part1(): Int = presentsAtHouses(1_000_000, 10).indexOfFirst { it > input } + 1

    override fun part2(): Int = presentsAtHouses(1_000_000, 11, 50).indexOfFirst { it > input } + 1

    fun presentsAtHouses(capacity: Int, nrOfPresents: Int, maxPresents: Int = Int.MAX_VALUE) = IntArray(capacity).also { arr ->
        (0 until capacity).forEach { idx ->
            var delivered = 0
            (idx until capacity step (idx + 1)).forEach { idx2 ->
                if (delivered < maxPresents) {
                    arr[idx2] += (idx + 1) * nrOfPresents
                    delivered += 1
                }
            }
        }
    }
}