package nl.avwie.aoc.y2022

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import nl.avwie.aoc.common.*

object Day14 : Day<Int, Int> {

    val blocked = parse(Input.inputLines(2022, 14))
    val floorLevel = blocked.maxOf { it.y } + 2

    override fun part1(): Int = simulate(blocked, Vector2D(500, 0)).blocked.size - blocked.size

    override fun part2(): Int = simulate(blocked, Vector2D(500, 0), floorLevel).blocked.size - blocked.size + 1

    fun parse(lines: Sequence<String>) = lines.flatMap(::parseLine).distinct().toPersistentSet()

    fun parseLine(line: String) = line
        .split(" -> ")
        .map { point -> point.split(",").let { (x, y) -> Vector2D(x.toInt(), y.toInt()) }}
        .let { Polygon(it).points }

    fun simulate(blocked: PersistentSet<Vector2D<Int>>, startPosition: Vector2D<Int>, floorLevel: Int? = null) =
        generateSequence(State(blocked, grain = startPosition, floorLevel = floorLevel)) { state ->
            val endState = state.simulate().last()
            when {
                endState.isAtRest && endState.grain != startPosition -> endState.copy(
                    blocked = state.blocked.add(endState.grain),
                    grain = Vector2D(500, 0)
                )
                else -> endState
            }
        }.dropUntil { state -> state.isFreeFalling || state.isAtRest }.first()

    data class State(val blocked: PersistentSet<Vector2D<Int>>, val grain: Vector2D<Int>, val floorLevel: Int? = null) {

        val nextPositions = listOf(
            grain + Vector2D(x =  0, y = 1),
            grain + Vector2D(x = -1, y = 1),
            grain + Vector2D(x =  1, y = 1),
        ).filter { position ->
            !blocked.contains(position) && position.y != floorLevel
        }

        val isAtRest = nextPositions.isEmpty()
        val isFreeFalling = floorLevel == null && blocked.all { position -> position.y < grain.y }

        fun next(): State? = when {
            isAtRest || isFreeFalling -> null
            else -> copy(grain = nextPositions.first())
        }

        fun simulate() = generateSequence(this) { state -> state.next() }
    }
}