package nl.avwie.aoc.common

enum class Direction {
    North,
    East,
    South,
    West;

    fun opposite(): Direction = when (this) {
        North -> South
        East -> West
        South -> North
        West -> East
    }
}

data class Complex(val i: Long = 0, val j: Long = 0) {

    constructor(i: Int, j: Int): this(i.toLong(), j.toLong())

    operator fun plus(other: Long) = plus(Complex(other, 0))
    operator fun plus(other: Int) = plus(Complex(other, 0))
    operator fun plus(other: Complex) = Complex(i + other.i, j + other.j)
    operator fun plus(pair: Pair<Long, Long>) = Complex(i + pair.first, j + pair.second)

    operator fun minus(other: Long) = minus(Complex(other, 0))
    operator fun minus(other: Int) = minus(Complex(other, 0))
    operator fun minus(other: Complex) = Complex(i - other.i, j - other.j)
    operator fun minus(pair: Pair<Long, Long>) = Complex(i - pair.first, j - pair.second)

    operator fun times(other: Complex) = Complex(i * other.i - j * other.j, i * other.j + j * other.i)
    operator fun times(pair: Pair<Long, Long>) = Complex(i * pair.first - j * pair.second, i * pair.second + j * pair.first)
    operator fun times(s: Long) = Complex(i * s, j * s)
    operator fun times(s: Int) = Complex(i * s, j * s)

    operator fun unaryMinus() = Complex(-i, -j)

    companion object {
        val ZERO = Complex(0, 0)
        val UNIT = Complex(1, 0)
        val j = Complex(0, 1)
    }
}