package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.search.DijkstraSearch

object Day15 : Day<Int, Int> {

    data class Step(val pos: Vector2D<Int>, val risk: Int, val previous: Step?) {
        override fun equals(other: Any?): Boolean = (other as? Step)?.pos == pos
        override fun hashCode(): Int = pos.hashCode()

        val totalRisk: Int = (previous?.totalRisk ?: 0) + risk
    }

    val map = parse(Input.inputLines(2021, 15))

    fun parse(lines: Sequence<String>): Map<Vector2D<Int>, Int> = lines.foldIndexed(mapOf()) { r, acc, line ->
        acc + line.mapIndexed { c, char -> Vector2D(c, r) to char.digitToInt() }
    }

    fun lowestRisk(map: Map<Vector2D<Int>, Int>): Int {
        val start = Vector2D(0, 0)
        val target = map.maxByOrNull { (k, _) -> k.x * k.x + k.y * k.y }!!.key
        val search = DijkstraSearch<Step>(
            found = { step -> step.pos == target },
            children = { step ->
                step.pos.neighbors(diagonal = false)
                    .filter { neighbor -> map.containsKey(neighbor) }
                    .map { neighbor -> Step(neighbor, map[neighbor]!!, step) }
            },
            cost = { step -> step.totalRisk.toDouble() }
        )

        val init = Step(start, 0, null)
        val result = search.search(init).first().totalRisk
        return result
    }


    override fun part1(): Int = lowestRisk(map)

    override fun part2(): Int {
        TODO("Not yet implemented")
    }
}