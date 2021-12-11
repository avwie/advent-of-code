package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day5 : Day<Int, Int> {

    private val lines = Input.inputLinesRegex(2021, 5,"(\\d*),(\\d*) -> (\\d*),(\\d*)")
        .map { coords -> coords.drop(1).map { c -> c.toInt() } }
        .map { (x0, y0, x1, y1) -> Vector2D(x0, y0) to Vector2D(x1, y1) }
        .toList()

    override fun part1(): Int = lines.countOverlaps(setOf(Alignment.Horizontal, Alignment.Vertical))
    override fun part2(): Int = lines.countOverlaps(setOf(Alignment.Horizontal, Alignment.Vertical, Alignment.Diagonal))

    private fun List<Pair<Vector2D<Int>, Vector2D<Int>>>.countOverlaps(allowedAlignments: Set<Alignment>): Int = this
        .filter { (begin, end) -> allowedAlignments.contains(Alignment.get(begin, end)) }
        .flatMap { (begin, end) -> (begin .. end).toList() }
        .groupBy { it }
        .count { (_, l) -> l.size > 1 }
}