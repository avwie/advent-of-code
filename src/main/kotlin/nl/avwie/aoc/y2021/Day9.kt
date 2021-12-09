package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.Map

object Day9 : Day<Int, Int> {

    val map = parse(Input.inputLines(2021, 9))

    override fun part1(): Int {
        val lowpoints = lowPoints(map)
        val risk = lowpoints.sumOf { risk(map, it) }
        TODO()
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun parse(lines: Sequence<String>): Map<Int> = Map.Default(
        values = lines.map { line -> line.map { it.digitToInt() } }.toList()
    )

    fun lowPoints(map: Map<Int>): List<Point> = (0 until map.nrows)
        .flatMap { r -> (0 until map.ncols).map { c -> r to c } }
        .map { (r, c) -> Point(r, c) }
        .filter { point ->
            val value = map.cell(point.x, point.y)
            offsets
                .filter { (dr, dc) -> dr == 0 || dc == 0 }
                .map { (dr, dc) -> Point(point.x + dr, point.y + dc) }
                .mapNotNull { map.cellOrNull(it.x, it.y) }
                .all { v -> v > value  }
        }

    fun risk(map: Map<Int>, point: Point): Int = map.cell(point.x, point.y) + 1
}