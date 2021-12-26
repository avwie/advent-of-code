package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day25 : Day<Int, Unit> {
    override fun part1(): Int = movement(parse(Input.inputLines(2021, 25).toList()))
        .windowed(2).withIndex().first { (_, h) -> h[0] == h[1] }.index + 1

    override fun part2() {}

    fun movement(init: State) = generateSequence(init) { it.next() }

    fun parse(lines: List<String>): State {
        val acc = mutableMapOf<Direction, MutableSet<Vector2D<Int>>>()
        val area = Area(lines.first().length, lines.size)
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                when (c) {
                    '>' -> acc.getOrPut(Direction.East) { mutableSetOf() }.add(Vector2D(x, y))
                    'v' -> acc.getOrPut(Direction.South) { mutableSetOf() }.add(Vector2D(x, y))
                }
            }
        }
        return State(acc, area)
    }

    data class State(val herds: Map<Direction, Set<Vector2D<Int>>>, val area: Area<Int>) {

        fun next() = moveHerd(Direction.East).moveHerd(Direction.South)

        private fun moveHerd(direction: Direction) = copy(
            herds = herds + (direction to herds[direction]!!.map { move(it, direction) }.toSet())
        )

        private fun move(position: Vector2D<Int>, direction: Direction): Vector2D<Int> {
            val nextPosition = position.advance(direction, 1).let { next ->
                when {
                    next.x >= area.width -> next.copy(x = 0)
                    next.y >= area.height -> next.copy(y = 0)
                    else ->  next
                }
            }

            if (herds.any { (_, s) -> s.contains(nextPosition) }) return position
            return nextPosition
        }
    }
}