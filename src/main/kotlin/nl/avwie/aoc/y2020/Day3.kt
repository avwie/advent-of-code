package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day3 : Day<Long, Long> {

    val input = Input.inputLines(2020, 3).toList()
    val map = Map(input)

    override fun part1(): Long {
        val hits = howManyTreesHit(map, 3, 1)
        return hits.toLong()
    }

    override fun part2(): Long {
        val slopes = listOf(
            (1 to 1),
            (3 to 1),
            (5 to 1),
            (7 to 1),
            (1 to 2)
        )

        val totalHits = slopes.fold(1L) { total, slope ->
            val (dx, dy) = slope
            val hits = howManyTreesHit(map, dx, dy).toLong()
            total * hits
        }
        return totalHits
    }

    fun howManyTreesHit(map: Map, dx: Int, dy: Int): Int {
        var x = 0
        var y = 0
        var hits = 0
        while (y < map.height) {
            if (map.isTree(x, y)) {
                hits++
            }
            x += dx
            y += dy
        }
        return hits
    }

    data class Map(val input: List<String>) {
        val height = input.size
        val width = input.first().length

        fun isTree(x: Int, y: Int): Boolean {
            return input[y][x % width] == '#'
        }
    }
}