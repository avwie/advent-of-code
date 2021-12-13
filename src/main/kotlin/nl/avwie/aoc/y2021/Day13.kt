package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day13 : Day<Int, String> {

    private val input = parse(Input.input(2021, 13))
    override fun part1(): Int = input.let { (points, folds) -> fold(points, folds[0].first, folds[0].second) }.size
    override fun part2(): String = input.let { (points, folds) ->  folds.fold(points) { acc, (x, y) -> fold(acc, x, y) } }.toImage()

    fun parse(input: String): Pair<Set<Vector2D<Int>>, List<Pair<Int, Int>>> = input.split("\n\n").let { (pointPart, foldPart) ->
        val points = pointPart.lines().map { it.split(",") }.map { (x, y) -> Vector2D(x.toInt(), y.toInt()) }.toSet()
        val folds = foldPart.lines().map { it.split("=") }.map { (l, c) -> if (l.last() == 'y') 0 to c.toInt() else c.toInt() to 0 }
        points to folds
    }

    fun fold(points: Set<Vector2D<Int>>, x: Int, y: Int) = points.map { point ->
        when {
            x > 0 && point.x >= x -> point.copy(x = x - (point.x - x))
            y > 0 && point.y >= y -> point.copy(y = y - (point.y - y))
            else -> point
        }
    }.toSet()
}