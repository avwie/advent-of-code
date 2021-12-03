package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.minMax

object Day9 : Day<Long, Long> {

    fun input() =  Input.inputLines(2020, 9).map { it.toLong() }

    override fun part1(): Long = input().windowed(26).first {
        !isSum(it.last(), it.subList(0, 26))
    }.last()

    override fun part2(): Long = (part1() to input().toList()).let { (answer, data) ->
        (2 .. data.size).asSequence()
            .flatMap { data.windowed(it) }
            .first { it.sum() == answer }
            .minMax().let { (min, max) -> min + max }
    }

    fun isSum(value: Long, numbers: List<Long>): Boolean {
        tailrec fun inner(a: Long, remaining: List<Long>): Boolean = when {
            remaining.any { b -> a + b == value && a != b } -> {
                true
            }
            remaining.size >= 2 -> {
                inner(remaining.first(), remaining.drop(1))
            }
            else -> {
                false
            }
        }
        return inner(numbers.first(), numbers.drop(1))
    }
}