package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day11 : Day<Int, Int> {

    data class Octopus(val energy: Int, val hasFlashed: Boolean) {
        val mustFlash = energy > 9 && !hasFlashed
    }

    private val input = parse(Input.inputLines(2021, 11))

    override fun part1(): Int = ockieSequence(input).scan(0) {
            acc, map -> acc + map.count { (_, ockie) -> ockie.energy == 0 }
    }.drop(100).first()

    override fun part2(): Int = ockieSequence(input).withIndex().first { v -> v.value.all { it.value.energy == 0 } }.index + 1

    fun parse(lines: Sequence<String>): Map<Vector2D<Int>, Octopus> = lines.flatMapIndexed { r, line ->
        line.mapIndexed { c, char -> Vector2D(c, r) to Octopus(char.digitToInt(), false) }
    }.toMap()

    fun ockieSequence(map: Map<Vector2D<Int>, Octopus>) = generateSequence(map) { step(it) }.drop(1)

    fun step(map: Map<Vector2D<Int>, Octopus>): Map<Vector2D<Int>, Octopus>  {
        val acc = map.mapValues { (_, v) -> v.copy(energy = v.energy + 1) }.toMutableMap()

        while (acc.any { (_, ockie) -> ockie.mustFlash }) {
            acc.filter { (_, ockie) -> ockie.mustFlash }.forEach { point, ockie ->
                acc[point] = ockie.copy(hasFlashed = true)
                point.neighbors().filter { acc.containsKey(it) }.forEach { neighborPoint ->
                    val neighborOckie = acc[neighborPoint]!!
                    acc[neighborPoint] = neighborOckie.copy(energy = neighborOckie.energy + 1)
                }
            }
        }

        // set to 0
        return acc.mapValues { (_, ockie) -> if (ockie.hasFlashed) ockie.copy(energy = 0, hasFlashed = false) else ockie }
    }
}