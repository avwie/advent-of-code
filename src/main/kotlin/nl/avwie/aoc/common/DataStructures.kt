package nl.avwie.aoc.common

import kotlin.math.sqrt

interface Board<T> {
    val nrows: Int
    val ncols: Int
    val values: List<T>

    fun cell(row: Int, col: Int): T = values[row * ncols + col]
    fun row(row: Int): List<T> = (0 until ncols).map { col -> cell(row, col) }
    fun col(col: Int): List<T> = (0 until nrows).map { row -> cell(row, col) }
    fun rows(): List<List<T>> = (0 until nrows).map { row -> row(row) }
    fun cols(): List<List<T>> = (0 until ncols).map { col -> col(col) }
}

interface SquareBoard<T> : Board<T> {
    val dim: Int get() = sqrt(values.size.toDouble()).toInt()
    override val ncols: Int get() = dim
    override val nrows: Int get() =  dim
}