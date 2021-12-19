package nl.avwie.aoc.common

import java.lang.IllegalArgumentException
import kotlin.math.*

enum class RotationalOrientation {
    Collinear,
    Clockwise,
    CounterClockwise
}

enum class Direction(val angle: Int) {
    North(0),
    East(90),
    South(180),
    West(270);

    fun opposite(): Direction = when (this) {
        North -> South
        East -> West
        South -> North
        West -> East
    }

    fun rotate(orientation: RotationalOrientation) = when (orientation) {
        RotationalOrientation.Collinear -> this
        RotationalOrientation.Clockwise -> fromAngle(this.angle + 90 + 360)!!
        RotationalOrientation.CounterClockwise -> fromAngle(this.angle - 90 + 360)!!
    }

    companion object {
        fun get(p1: Vector2D<Int>, p2: Vector2D<Int>): Direction? = when (Alignment.get(p1, p2)) {
            Alignment.Horizontal -> if (p1.x < p2.x) West else East
            Alignment.Vertical -> if (p1.y < p2.y) South else North
            else -> null
        }

        fun fromAngle(angle: Int): Direction? = values().firstOrNull { it.angle == angle % 360 }
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

    fun contains(pos: Vector2D<T>): Boolean = when {
        ops.compare(pos.x, x0) == -1 -> false
        ops.compare(pos.y, y0) == -1 -> false
        ops.compare(pos.x, x1) == 1 -> false
        ops.compare(pos.y, y1) == 1 -> false
        else -> true
    }

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

data class DirectedVector<T>(val position: Vector2D<T>, val direction: Direction) {
    private val ops = position.ops

    fun advance(amount: T): DirectedVector<T> = when (direction) {
        Direction.North -> copy(position = position + Vector2D(ops.zero, ops.neg(amount), ops))
        Direction.East -> copy(position = position + Vector2D(amount, ops.zero, ops))
        Direction.South -> copy(position = position + Vector2D(ops.zero, amount, ops))
        Direction.West -> copy(position = position + Vector2D(ops.neg(amount), ops.zero, ops))
    }

    fun rotate(rotationalOrientation: RotationalOrientation) = copy(direction = direction.rotate(rotationalOrientation))
}

data class Vector3D<T>(val x: T, val y: T, val z: T, val ops: NumericalOps<T>) {
    operator fun plus(other: Vector3D<T>) = Vector3D(ops.plus(x, other.x), ops.plus(y, other.y), ops.plus(z, other.z), ops)
    operator fun minus(other: Vector3D<T>) = Vector3D(ops.minus(x, other.x), ops.minus(y, other.y), ops.minus(z, other.z), ops)
    operator fun times(scalar: T) = Vector3D(ops.times(scalar, x), ops.times(scalar, y), ops.times(scalar, z), ops)
    operator fun unaryMinus() = Vector3D(ops.neg(x), ops.neg(y), ops.neg(z), ops)

    companion object {
        val X = Vector3D(1.0, 0.0, 0.0)
        val Y = Vector3D(0.0, 1.0, 0.0)
        val Z = Vector3D(0.0, 0.0, 1.0)
        inline operator fun <reified T> invoke(x: T, y: T, z: T) = Vector3D(x, y, z, NumericalOps.get())
    }
}

data class Quaternion(val r: Double, val i: Double, val j: Double, val k: Double) {

    fun conj(): Quaternion = Quaternion(r, -i, -j, -k)

    operator fun times(other: Quaternion) = hamilton(this, other)

    companion object {

        val UNIT = Quaternion(1.0, 0.0, 0.0, 0.0)

        val rotations = (listOf(
            UNIT,
            rotation(PI / 2, 0.0, 0.0),
            rotation(PI, 0.0, 0.0),
            rotation(3 * PI / 2, 0.0, 0.0),
            rotation(0.0, PI / 2, 0.0),
            rotation(0.0, 3 * PI / 2, 0.0)
        ) to listOf(
            rotation(0.0, 0.0, 0.0),
            rotation(0.0, 0.0, PI / 2),
            rotation(0.0, 0.0, PI),
            rotation(0.0, 0.0, 3 * PI / 2)
        )).let { (a, b) -> product(a, b) { q1, q2 -> q1 * q2} }

        fun fromAngle(theta: Double, u1: Double, u2: Double, u3: Double): Quaternion {
            val c = cos(theta / 2)
            val s = sin(theta / 2)
            return Quaternion(c, u1 * s, u2 * s, u3 * s)
        }

        fun fromAngle(theta: Double, vector3D: Vector3D<Double>): Quaternion {
            return fromAngle(theta, vector3D.x, vector3D.y, vector3D.z)
        }

        fun rotation(x: Double, y: Double, z: Double): Quaternion {
            val qx = fromAngle(x, Vector3D.X)
            val qy = fromAngle(y, Vector3D.Y)
            val qz = fromAngle(z, Vector3D.Z)
            return qx * qy * qz
        }

        fun fromVector(vector3D: Vector3D<Double>): Quaternion {
            val (b, c, d, _) = vector3D
            return Quaternion(0.0, b, c, d)
        }

        fun hamilton(left: Quaternion, right: Quaternion): Quaternion {
            val (a1, b1, c1, d1) = left
            val (a2, b2, c2, d2) = right

            val a3 = a1 * a2 - b1 * b2 - c1 * c2 - d1 * d2
            val b3 = a1 * b2 + b1 * a2 + c1 * d2 - d1 * c2
            val c3 = a1 * c2 - b1 * d2 + c1 * a2 + d1 * b2
            val d3 = a1 * d2 + b1 * c2 - c1 * b2 + d1 * a2
            return Quaternion(a3, b3, c3, d3)
        }
    }
}

fun Vector3D<Double>.rotate(q: Quaternion): Vector3D<Double> {
    val p = Quaternion.fromVector(this)
    val (_, x, y, z) = q * p * q.conj()
    return Vector3D(x, y, z)
}

fun Vector3D<Double>.rotate(i: Double, j: Double, k: Double): Vector3D<Double> {
    val p = Quaternion.fromVector(this)
    val q = Quaternion.rotation(i, j, k)
    val (_, x, y, z) = q * p * q.conj()
    return Vector3D(x, y, z)
}

fun Vector3D<Double>.roundToInt(epsilon: Double = 1E-9): Vector3D<Int> {
    val (x, y, z, _) = this
    require(listOf(x, y, z).none { abs(x - x.roundToInt()) > epsilon })
    return Vector3D(x.roundToInt(), y.roundToInt(), z.roundToInt())
}

fun Vector3D<Int>.toDouble() = Vector3D(x.toDouble(), y.toDouble(), z.toDouble())


fun Vector2D<Int>.neighbors(diagonal: Boolean = true): List<Vector2D<Int>> =
    when {
        diagonal -> offsets
        else -> offsets.filter { (dx, dy) -> dx == 0 || dy == 0 }
    }.map { (dx, dy) -> copy(x = x + dx, y = y + dy) }