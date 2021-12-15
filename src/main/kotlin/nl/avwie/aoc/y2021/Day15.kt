package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.search.DijkstraSearch
import nl.avwie.aoc.common.search.dijkstraSearch
import java.lang.Integer.max

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

    override fun part1(): Int = lowestRisk(map)
    override fun part2(): Int = lowestRisk(map.repeat(4, Alignment.Horizontal).repeat(4, Alignment.Vertical))

    fun lowestRisk(input: Map<Vector2D<Int>, Int>): Int = input.maxKey().let { target ->
        dijkstraSearch(
            init = Step(Vector2D(0, 0), 0, null),
            found = { step -> step.pos == target },
            children = { step ->
                step.pos.neighbors(diagonal = false)
                    .filter { neighbor -> input.containsKey(neighbor) }
                    .map { neighbor -> Step(neighbor, input[neighbor]!!, step) }
            },
            cost = { step -> step.totalRisk.toDouble() }
        ).first().totalRisk
    }

    private fun Map<Vector2D<Int>, Int>.maxKey(): Vector2D<Int> = this.maxByOrNull { (k, _) -> k.x * k.x + k.y * k.y }!!.key
    private fun Map<Vector2D<Int>, Int>.repeat(times: Int, alignment: Alignment) : Map<Vector2D<Int>, Int> = this.maxKey().let { bottomRight ->
            (1 .. times).fold(this) { acc, i ->
                acc + this.map { (k, v) ->
                    k.copy(
                        x = if (alignment == Alignment.Horizontal) k.x + i * (bottomRight.x + 1) else k.x,
                        y = if (alignment == Alignment.Vertical)   k.y + i * (bottomRight.y + 1) else k.y
                    ) to if ((v + i) % 9 == 0) 9 else (v + i) % 9
                }
            }
        }
}
