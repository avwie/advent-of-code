package nl.avwie.aoc.common

import java.lang.IllegalArgumentException

interface NumericalOps<T> {
    fun plus(left: T, right: T): T
    fun minus(left: T, right: T): T
    fun times(left: T, right: T): T
    fun div(left: T, right: T): T

    fun min(left: T, right: T): T
    fun max(left: T, right: T): T

    fun compare(left: T, right: T): Int
    fun neg(value: T): T


    val zero: T
    val unit: T

    @Suppress("UNCHECKED_CAST")
    companion object {

        inline fun <reified T> get(): NumericalOps<T> = when (T::class) {
            Int::class -> Int32
            Long::class -> Int64
            Double::class -> Float64
            else -> throw IllegalArgumentException("Invalid numerical op")
        } as NumericalOps<T>
    }
}

interface Integral

object Int32 : NumericalOps<Int>, Integral {
    override fun plus(left: Int, right: Int): Int = left + right
    override fun minus(left: Int, right: Int): Int = left - right
    override fun times(left: Int, right: Int): Int = left * right
    override fun div(left: Int, right: Int): Int = left / right
    override fun min(left: Int, right: Int): Int = kotlin.math.min(left, right)
    override fun max(left: Int, right: Int): Int =  kotlin.math.max(left, right)
    override fun compare(left: Int, right: Int): Int= left.compareTo(right)
    override fun neg(value: Int): Int = -value

    override val zero: Int = 0
    override val unit: Int = 1

    tailrec fun gcd(a: Int, b: Int): Int {
        if (a == b) return a
        if (b == zero) {
            if (a == zero) return 1
            else return a
        }
        else return gcd(b, a % b)
    }
}

object Int64 : NumericalOps<Long>, Integral {
    override fun plus(left: Long, right: Long): Long = left + right
    override fun minus(left: Long, right: Long): Long = left - right
    override fun times(left: Long, right: Long): Long = left * right
    override fun div(left: Long, right: Long): Long = left / right
    override fun min(left: Long, right: Long): Long = kotlin.math.min(left, right)
    override fun max(left: Long, right: Long): Long = kotlin.math.max(left, right)
    override fun compare(left: Long, right: Long): Int = left.compareTo(right)
    override fun neg(value: Long): Long = -value

    override val zero: Long = 0L
    override val unit: Long = 1L

    tailrec fun gcd(a: Long, b: Long): Long {
        if (a == b) return a
        if (b == zero) {
            if (a == zero) return 1
            else return a
        }
        else return gcd(b, a % b)
    }
}

object Float64 : NumericalOps<Double> {
    override fun plus(left: Double, right: Double): Double = left + right
    override fun minus(left: Double, right: Double): Double = left - right
    override fun times(left: Double, right: Double): Double = left * right
    override fun div(left: Double, right: Double): Double = left / right
    override fun min(left: Double, right: Double): Double = kotlin.math.min(left, right)
    override fun max(left: Double, right: Double): Double = kotlin.math.max(left, right)
    override fun compare(left: Double, right: Double): Int = left.compareTo(right)
    override fun neg(value: Double): Double = -value

    override val zero: Double = 0.0
    override val unit: Double = 1.0
}