package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day6 : Day<Long, Long> {

    private val start = parse(Input.input(2021, 6))

    override fun part1(): Long = solve(start, 80)
    override fun part2(): Long = solve(start, 256)

    fun parse(input: String): List<Generation> = input
        .split(",").map { it.toInt() }
        .map { Generation(it, 1) }.consolidate()

    private fun solve(generations: List<Generation>, days: Int): Long = (1 .. days).fold(generations) { acc, _ ->
        acc.flatMap { it.next() }.consolidate()
    }.sumOf { it.count }

    private fun List<Generation>.consolidate(): List<Generation> = this
        .groupBy { it.timer }
        .map { (timer, generations) -> Generation(timer, generations.sumOf { it.count }) }

    data class Generation(val timer: Int, val count: Long) {
        fun next(): List<Generation> = when (timer) {
            0 -> listOf(this.copy(timer = 6), this.copy(timer = 8))
            else -> listOf(this.copy(timer = timer - 1))
        }
    }
}