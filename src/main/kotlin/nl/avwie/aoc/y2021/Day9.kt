package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day9 : Day<Int, Int> {

    val map = parse(Input.inputLines(2021, 9))

    private val offsets = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    override fun part1(): Int = lowPoints(map).sumOf { risk(map, it) }

    override fun part2(): Int = lowPoints(map)
        .map { lowPoint -> basin(map, lowPoint).size }
        .sortedBy { -it }
        .take(3)
        .fold(1) { acc, s -> acc * s}

    fun parse(lines: Sequence<String>): Map<Pair<Int, Int>, Int> = lines.flatMapIndexed { r, line ->
        line.mapIndexed { c, char -> (r to c) to char.digitToInt() }
    }.toMap()

    fun lowPoints(map: Map<Pair<Int, Int>, Int>): List<Pair<Int, Int>> {
        val rows = map.keys.map { it.first }.maxOrNull()!! + 1
        val cols = map.keys.map { it.second }.maxOrNull()!! + 1
        val indices = (0 until rows).flatMap { r -> (0 until cols).map { c -> r to c } }
        return indices
            .filter { (r, c) ->
                val value = map[r to c]!!
                offsets
                    .mapNotNull { (dr, dc) -> map[(r +  dr) to (c + dc)] }
                    .all { it > value }
            }
    }

    fun risk(map: Map<Pair<Int, Int>, Int>, point: Pair<Int, Int>): Int = map[point]!! + 1

    fun basin(map: Map<Pair<Int, Int>, Int>, lowPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val acc = mutableSetOf(lowPoint)
        var frontier = frontier(map, acc)
        while (frontier.isNotEmpty()) {
            acc.addAll(frontier)
            frontier = frontier(map, acc)
        }
        return acc
    }

    private fun frontier(map: Map<Pair<Int, Int>, Int>, points: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val allPoints = points.flatMap { (r, c) -> offsets.map { (dr, dc) ->  (r + dr) to (c + dc) } }.toSet()
        return (allPoints - points).filter { point -> (map[point] ?: 9) != 9 }.toSet()
    }
}