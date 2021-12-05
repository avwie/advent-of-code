package nl.avwie.aoc.common

import kotlin.math.abs

enum class Direction {
    North,
    East,
    South,
    West,
    Indeterminate;

    fun opposite(): Direction = when (this) {
        North -> South
        East -> West
        South -> North
        West -> East
        Indeterminate -> Indeterminate
    }

    companion object {
        fun get(p1: Point, p2: Point) = when (Alignment.get(p1, p2)) {
            Alignment.Horizontal -> if (p1.x < p2.x) West else East
            Alignment.Vertical -> if (p1.y < p2.y) South else North
            else -> Indeterminate
        }
    }
}

enum class Alignment {
    Horizontal,
    Vertical,
    Diagonal,
    Indeterminate;

    companion object {
        fun get(p1: Point, p2: Point) = when {
            p1.x == p2.x && p1.y != p2.y -> Vertical
            p1.x != p2.x && p1.y == p2.y -> Horizontal
            abs(p1.x - p2.x) == abs(p1.y - p2.y) -> Diagonal
            else -> Indeterminate
        }
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point): Sequence<Point> {
        val alignment = Alignment.get(this, other)
        require(alignment != Alignment.Indeterminate) { "Alignment doesn't allow for range" }

        val dx = when {
            this.x < other.x -> 1
            this.x > other.x -> -1
            else -> 0
        }

        val dy = when {
            this.y < other.y -> 1
            this.y > other.y -> -1
            else -> 0
        }

        return generateSequence(this) { current ->
            current.copy(x = current.x + dx, y = current.y + dy)
        }.takeWhileInclusive { current -> current != other }
    }
}