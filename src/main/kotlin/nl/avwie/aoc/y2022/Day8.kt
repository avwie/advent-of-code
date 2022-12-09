package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.*


object Day8 : Day<Int, Long> {

    val map = Input.inputLines(2022, 8).parse()

    override fun part1(): Int = map
        .filter { (position, _) -> isVisible(position, map) }
        .count()

    override fun part2(): Long = map
        .map { (position, _) -> viewingDistance(position, map) }
        .max()

    fun Sequence<String>.parse() = flatMapIndexed { rowNumber, line ->
        line.mapIndexed  { colNumber, char ->
            Vector2D(colNumber, rowNumber) to char.digitToInt()
        }
    }.toMap()

    fun isVisible(position: Vector2D<Int>, map: Map<Vector2D<Int>, Int>): Boolean =
        getAllDirectionSequences(position, map)
            .any { direction ->
                direction.all { other ->
                    map[other]!! < map[position]!!
                }
            }

    fun viewingDistance(position: Vector2D<Int>, map: Map<Vector2D<Int>, Int>): Long =
        getAllDirectionSequences(position, map)
            .map { direction ->
                direction.takeWhileInclusive { other ->
                    map[other]!! < map[position]!!
                }.count()
            }
            .fold(1) { a, b -> a * b }

    fun getAllDirectionSequences(position: Vector2D<Int>, map: Map<Vector2D<Int>, Int>): List<Sequence<Vector2D<Int>>> {
        val bounds = map.maxKey()
        return Direction.All.map { getDirectionSequence(position, bounds, it) }
    }

    private fun getDirectionSequence(origin: Vector2D<Int>, bounds: Vector2D<Int>, direction: Direction) = when (direction) {
        Direction.North -> origin.rangeTo(Vector2D(origin.x, 0))
        Direction.East -> origin.rangeTo(Vector2D(bounds.x, origin.y))
        Direction.South -> origin.rangeTo(Vector2D(origin.x, bounds.y))
        Direction.West -> origin.rangeTo(Vector2D(0, origin.y))
    }.drop(1)

    private fun Map<Vector2D<Int>, Int>.maxKey(): Vector2D<Int> = this.maxByOrNull { (k, _) -> k.x * k.x + k.y * k.y }!!.key
}