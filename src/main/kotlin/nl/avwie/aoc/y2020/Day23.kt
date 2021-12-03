package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.reduceRepeated
import nl.avwie.aoc.common.toDigits
import nl.avwie.aoc.common.toLong

object Day23 : Day<Long, Long> {

    override fun part1(): Long = play(459672813L.toDigits(), 9, 100).let { result ->
        listOf(result[1]).reduceRepeated(result.size - 3) { acc -> acc + result[acc.last()] }
    }.toLong()

    override fun part2(): Long = play(459672813L.toDigits(), 1_000_000, 10_000_000).let { result ->
        val a = result[1]
        val b = result[a]
        a.toLong() * b.toLong()
    }

    fun play(seed: List<Int>, size: Int, rounds: Int): IntArray {
        val cups = IntArray(size) { i -> i + 1 }
        seed.forEachIndexed { i, d -> cups[i] = d }

        val lookup  = IntArray(size + 1)
        (0 until size).forEach { i ->
            lookup[cups[i]] = cups[(i + 1) % size]
        }

        var current = seed.first()
        repeat(rounds) {
            val picked = IntArray(3) { 0 }
            picked[0] = lookup[current]
            picked[1] = lookup[picked[0]]
            picked[2] = lookup[picked[1]]
            lookup[current] = lookup[picked[2]]

            var destination = if (current - 1 <= 0) (lookup.size - 1) else current - 1
            while (picked.contains(destination)) {
                destination = if (destination -1 ==  0) (lookup.size - 1) else destination - 1
            }

            picked.forEachIndexed { i, d ->
                lookup[d] = if (i == picked.size - 1) lookup[destination] else picked[i + 1]
            }
            lookup[destination] = picked[0]
            current = lookup[current]
        }
        return lookup
    }

    fun order(cups: IntArray): List<Int> {
        val result = mutableListOf(cups[1])
        while (result.last() != 1) {
            result.add(cups[result.last()])
        }
        return result.dropLast(1)
    }
}