package nl.avwie.aoc.common

import kotlin.math.sqrt

interface Map<T> {
    val nrows: Int
    val ncols: Int
    val values: List<T>

    fun cellOrNull(row: Int, col: Int) = values.getOrNull(row * ncols + col)
    fun cell(row: Int, col: Int): T = cellOrNull(row, col)!!
    fun row(row: Int): List<T> = (0 until ncols).map { col -> cell(row, col) }
    fun col(col: Int): List<T> = (0 until nrows).map { row -> cell(row, col) }
    fun rows(): List<List<T>> = (0 until nrows).map { row -> row(row) }
    fun cols(): List<List<T>> = (0 until ncols).map { col -> col(col) }

    data class Default<T>(override val nrows: Int, override val ncols: Int, override val values: List<T>) :  Map<T> {
        constructor(values: List<List<T>>) : this(values.size, values.first().size, values.flatten())
    }
}

interface SquareMap<T> : Map<T> {
    val dim: Int get() = sqrt(values.size.toDouble()).toInt()
    override val ncols: Int get() = dim
    override val nrows: Int get() =  dim

    data class Default<T>(override val values: List<T>): SquareMap<T>
}

class CountingSet<T> {
    private val counts = mutableMapOf<T, Long>()

    fun add(item: T, count: Long): Long {
        counts[item] = count(item) + count
        return count(item)
    }

    fun add(item: T): Long = add(item, 1)
    fun count(item: T): Long = counts[item] ?: 0L
    fun remove(item: T): Long = counts.remove(item) ?: 0L
    fun items(): List<Pair<T, Long>> = counts.asIterable().map { (k, v) -> k to v }
    fun isEmpty() = counts.isEmpty()
    fun isNotEmpty() = counts.isNotEmpty()
}