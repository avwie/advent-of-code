package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day

object Day15 : Day<Long, Long> {

    override fun part1(): Long = solve(listOf(2,1,10,11,0,6), 2020).toLong()
    override fun part2(): Long = solve(listOf(2,1,10,11,0,6), 30000000).toLong()

    fun solve(start: List<Int>, count: Int): Int {

        val memory = IntArray(count) { -1 }
        start.take(start.size - 1).forEachIndexed { i, n -> memory[n] = i }

        tailrec fun inner(turn: Int, lastSpoken: Int): Int {
            if (turn == count - 1)
                return lastSpoken

            return if (memory[lastSpoken] == -1) {
                memory[lastSpoken] = turn
                inner(turn + 1, 0)
            } else {
                val spoken = turn - memory[lastSpoken]
                memory[lastSpoken] = turn
                inner(turn + 1, spoken)
            }
        }

        return inner(start.size - 1, start.last())
    }
}