import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.Vector2D
import nl.avwie.aoc.common.manhattan
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day15 : Day<Int, Int> {

    val REGEX = Regex(".*x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)")
    val pairs = parse(Input.inputLines(2022, 15))

    override fun part1(): Int = getBlockedCount(pairs, 2000000)

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun getBlockedCount(pairs: List<Pair<Vector2D<Int>, Vector2D<Int>>>, ycoord: Int): Int {
        val beacons = pairs
            .map { (_, beacon) -> beacon }
            .filter { it.y == ycoord }
            .map { it.x }
            .toSet()

        val (left, right) = pairs.fold(Int.MAX_VALUE to Int.MIN_VALUE) { (left, right), pair ->
            pair.horizontalEdge(ycoord)?.let {
                min(left, it.first) to max(right, it.second)
            } ?: (left to right)
        }

        return (left..right)
            .map { Vector2D(it, ycoord) }
            .filter { position ->
                !beacons.contains(position.x)
            }.count { position ->
                pairs.any { pair ->
                    pair.isInRange(position)
                }
            }
    }

    fun parse(lines: Sequence<String>): List<Pair<Vector2D<Int>, Vector2D<Int>>> = lines
        .map { line -> REGEX.matchEntire(line)!!.destructured }
        .map { (sx, sy, bx, by) -> Vector2D(sx.toInt(), sy.toInt()) to Vector2D(bx.toInt(), by.toInt()) }
        .toList()

    fun Pair<Vector2D<Int>, Vector2D<Int>>.isInRange(position: Vector2D<Int>) = this.let { (sensor, beacon) ->
        position.manhattan(sensor) <= sensor.manhattan(beacon)
    }

    fun Pair<Vector2D<Int>, Vector2D<Int>>.horizontalEdge(ycoord: Int) = this.let { (sensor, beacon) ->
        val coverage = sensor.manhattan(beacon)
        val distance = abs(sensor.y - ycoord)
        val remaining = coverage - distance
        when {
            distance > coverage -> null
            else -> sensor.x - remaining to sensor.x + remaining
        }
    }
}