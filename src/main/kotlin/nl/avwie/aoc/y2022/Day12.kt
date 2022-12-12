package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.search.dijkstraSearch

object Day12 : Day<Int, Int> {

    val map = parseLineSequenceToMap(Input.inputLines(2022, 12))

    override fun part1(): Int = search(map, map.entries.first { (_, char) -> char == 'S' }.key)
        .first()
        .steps

    override fun part2(): Int = map
        .filter { (_, char) -> char == 'a' }.keys
        .map { startingPoint -> search(map, startingPoint).firstOrNull()?.steps ?: Int.MAX_VALUE }
        .min()

    data class Step(val position: Vector2D<Int>, val steps: Int, val previous: Step?) {
        override fun hashCode(): Int = position.hashCode()
        override fun equals(other: Any?): Boolean = (other as? Step)?.position == position
    }

    fun search(map: Map<Vector2D<Int>, Char>, startingPoint: Vector2D<Int>) = dijkstraSearch(
        init = Step(position = startingPoint, steps = 0, previous = null),
        children = { step ->
            step.position.neighbors(diagonal = false)
                .filter { neighbor -> map.containsKey(neighbor) }
                .filter { neighbor ->
                    when {
                        map[step.position] == 'S' -> true
                        map[neighbor] == 'E' && map[step.position] != 'z' -> false
                        else -> map[neighbor]!!.code - map[step.position]!!.code <= 1
                    }
                }
                .map { neighbor -> Step(position = neighbor, steps = step.steps + 1, step) }
        },
        found = { step -> map[step.position] == 'E' },
        cost = { step -> step.steps.toDouble() }
    )
}