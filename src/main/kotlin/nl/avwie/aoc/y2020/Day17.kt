package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.combinations
import nl.avwie.aoc.common.reduceRepeated

object Day17 : Day<Long, Long> {

    val input = Input.inputLines(2020, 17).toList()
    private val offsetCache = mutableMapOf<Int, List<List<Int>>>()
    private val neighborCache = mutableMapOf<List<Int>, List<List<Int>>>()

    override fun part1(): Long = parse(input, 3).reduceRepeated(6) { step(it) }.size.toLong()
    override fun part2(): Long = parse(input, 4).reduceRepeated(6) { step(it) }.size.toLong()

    fun parse(lines: List<String>, dims: Int): Set<List<Int>> = lines.foldIndexed(setOf()) { y, set, line ->
        set + line.mapIndexedNotNull { x, c ->
            when (c) {
                '#' -> listOf(x, y) + List(dims - 2) { 0 }
                else -> null
            }
        }
    }

    fun step(points: Set<List<Int>>): Set<List<Int>> {
        val boundary = mutableSetOf<List<Int>>()
        val next = mutableSetOf<List<Int>>()

        points.forEach { point ->
            neighborsOf(point).partition { n -> points.contains(n) }.also { (onPoints, onBoundary) ->
                if (onPoints.size in 2..3) next.add(point)
                boundary.addAll(onBoundary)
            }
        }

        boundary.forEach { inactive ->
            if (neighborsOf(inactive).count { n -> points.contains(n) } == 3) {
                next.add(inactive)
            }
        }
        return next
    }

    private fun neighborOffsets(dims: Int) = offsetCache.getOrPut(dims) { listOf(-1, 0, 1).combinations(dims).filter { c -> !c.all { it == 0 } } }

    private fun neighborsOf(point: List<Int>): List<List<Int>> = neighborCache.getOrPut(point) {
        neighborOffsets(point.size).map { neighbor -> point.zip(neighbor).map { (i, di) -> i + di } }
    }
}