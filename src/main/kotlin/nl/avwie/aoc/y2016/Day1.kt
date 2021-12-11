package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*
import kotlin.math.absoluteValue

object Day1 : Day<Int, Int> {

    private val path = parse(Input.input(2016, 1))

    override fun part1(): Int = path.last().let { it.x.absoluteValue + it.y.absoluteValue }

    override fun part2(): Int = path
        .scan(setOf<Vector2D<Int>>()) { visited, next -> visited + next }
        .zip(path)
        .first { (visited, position) -> visited.contains(position) }
        .second.let { pos -> pos.x.absoluteValue + pos.y.absoluteValue }

    fun parse(line: String): List<Vector2D<Int>> = line.split(", ")
        .map { cmd ->
            when (cmd[0]) {
                'L' -> RotationalOrientation.CounterClockwise
                else -> RotationalOrientation.Clockwise
            } to cmd.drop(1).toInt()
        }
        .scan(DirectedVector(Vector2D(0, 0), Direction.North)) { acc, (r, d) ->
            acc.rotate(r).advance(d)
        }
        .map { dv -> dv.position }
        .windowed(2)
        .flatMap { (from, to) ->  (from .. to).drop(1) }
}