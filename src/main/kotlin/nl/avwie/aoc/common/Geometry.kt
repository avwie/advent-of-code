package nl.avwie.aoc.common

import kotlin.math.abs

enum class RotationalOrientation {
    Collinear,
    Clockwise,
    CounterClockwise
}

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
        fun get(p1: Vector2D<Int>, p2: Vector2D<Int>) = when (Alignment.get(p1, p2)) {
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
        fun get(p1: Vector2D<Int>, p2: Vector2D<Int>) = when {
            p1.x == p2.x && p1.y != p2.y -> Vertical
            p1.x != p2.x && p1.y == p2.y -> Horizontal
            abs(p1.x - p2.x) == abs(p1.y - p2.y) -> Diagonal
            else -> Indeterminate
        }
    }
}

data class Vector2D<T>(val x: T, val y: T, val ops: NumericalOps<T>) {
    operator fun plus(other: Vector2D<T>) = Vector2D(ops.plus(x, other.x), ops.plus(y, other.y), ops)
    operator fun minus(other: Vector2D<T>) = Vector2D(ops.minus(x, other.x), ops.minus(y, other.y), ops)

    operator fun rangeTo(other: Vector2D<T>): Sequence<Vector2D<T>> {
        require(ops is Integral) { "Only works for Integral values" }

        val dx = when (ops.compare(this.x, other.x)) {
            -1 -> ops.unit
            1 -> ops.neg(ops.unit)
            else -> ops.zero
        }

        val dy = when (ops.compare(this.y, other.y)) {
            -1 -> ops.unit
            1 -> ops.neg(ops.unit)
            else -> ops.zero
        }

        return generateSequence(this) { current ->
            current.copy(x = ops.plus(current.x, dx), y = ops.plus(current.y, dy))
        }.takeWhileInclusive { current -> current != other }
    }

    companion object {
        inline operator fun <reified T> invoke(x: T, y: T) = Vector2D(x, y, NumericalOps.get())

        inline fun <reified T> orientation(p: Vector2D<T>, q: Vector2D<T>, r: Vector2D<T>): RotationalOrientation =
            orientation(p, q, r, NumericalOps.get())

        fun <T> orientation(p: Vector2D<T>, q: Vector2D<T>, r: Vector2D<T>, ops: NumericalOps<T>): RotationalOrientation {
            val a = ops.minus(q.y, p.y)
            val b = ops.minus(r.x, q.x)
            val c = ops.minus(q.x, p.x)
            val d = ops.minus(r.y, q.y)
            val z = ops.minus(ops.times(a, b), ops.times(c, d))

            return when (z) {
                ops.zero -> RotationalOrientation.Collinear
                ops.max(z, ops.zero) -> RotationalOrientation.Clockwise
                else -> RotationalOrientation.CounterClockwise
            }
        }
    }
}

data class Line<T>(val start: Vector2D<T>, val end: Vector2D<T>) {
    val ops: NumericalOps<T> = start.ops

    fun intersects(other: Line<T>): Boolean {
        val p1 = this.start
        val q1 = this.end
        val p2 = other.start
        val q2 = other.end

        val o1 = Vector2D.orientation(p1, q1, p2, ops)
        val o2 = Vector2D.orientation(p1, q1, q2, ops)
        val o3 = Vector2D.orientation(p2, q2, p1, ops)
        val o4 = Vector2D.orientation(p2, q2, q1, ops)

        return (o1 != o2 && o3 != o4)
    }
}

data class Area<T>(val width: T, val height: T)

data class Rectangle<T>(val origin: Vector2D<T>, val area: Area<T>, private val ops: NumericalOps<T>) {

    constructor(x: T, y: T, width: T, height: T, ops: NumericalOps<T>): this(Vector2D(x, y, ops), Area(width, height), ops)

    val x0 = origin.x
    val y0 = origin.y
    val x1 = ops.plus(origin.x, area.width)
    val y1 = ops.plus(origin.y, area.height)
    val width = area.width
    val height = area.height

    fun move(d: Vector2D<T>) : Rectangle<T> = copy(origin = origin + d)

    operator fun plus(other: Rectangle<T>): Rectangle<T> {
        val u0 = ops.min(x0, other.x0)
        val v0 = ops.min(y0, other.y0)
        val u1 = ops.max(x1, other.x1)
        val v1 = ops.max(y1, other.y1)

        val width = ops.minus(u1, u0)
        val height = ops.min(v1, v0)
        return Rectangle(u0, v0, width, height, ops)
    }
}


fun Vector2D<Int>.neighbors(diagonal: Boolean = true): List<Vector2D<Int>> =
    when {
        diagonal -> offsets
        else -> offsets.filter { (dx, dy) -> dx == 0 || dy == 0 }
    }.map { (dx, dy) -> copy(x = x + dx, y = y + dy) }