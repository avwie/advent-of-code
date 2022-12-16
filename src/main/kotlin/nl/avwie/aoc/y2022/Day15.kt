import nl.avwie.aoc.common.*
import kotlin.math.abs

object Day15 : Day<Int, Long> {

    val REGEX = Regex(".*x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)")
    val pairs = parse(Input.inputLines(2022, 15))

    override fun part1(): Int = getBlockedCount(pairs, 2000000)

    override fun part2(): Long = findPosition(pairs, 4000000).let {
        it.x * 4000000L + it.y
    }

    fun getBlockedCount(pairs: List<Pair<Vector2D<Int>, Vector2D<Int>>>, ycoord: Int): Int {
        val beacons = pairs
            .map { (_, beacon) -> beacon }
            .filter { it.y == ycoord }
            .map { it.x }
            .toSet()

        val intervals = pairs
            .mapNotNull { pair -> pair.horizontalEdge(ycoord) }
            .merge()

        return intervals.sumOf { it.size() } - beacons.size
    }

    fun findPosition(pairs: List<Pair<Vector2D<Int>, Vector2D<Int>>>, bounds: Int): Vector2D<Int> {
        val (y, intervals) = (0 .. bounds)
            .asSequence()
            .mapIndexed { i, ycoord ->
                i to pairs
                    .mapNotNull { pair -> pair.horizontalEdge(ycoord) }
                    .merge()
            }
            .filter { it.second.size == 2 }
            .first()

        val x = intervals.sortedBy { it.from }.first().to + 1
        return Vector2D(x, y)
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
            else -> Interval(sensor.x - remaining, sensor.x + remaining)
        }
    }
}