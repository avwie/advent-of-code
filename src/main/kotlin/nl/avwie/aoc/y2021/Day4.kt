package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import kotlin.math.sqrt


object Day4 : Day<Int, Int> {

    private val finalBoards = parse(Input.input(2021, 4)).let { (draws, boards) -> play(draws, boards) }

    override fun part1(): Int = finalBoards.filter { it.isWinner() }.minByOrNull { it.turns }!!.score()
    override fun part2(): Int = finalBoards.filter { it.isWinner() }.maxByOrNull { it.turns }!!.score()

    private fun play(draws: List<Int>, boards: List<Board>): List<Board> {
        draws.forEach { draw ->
            boards.forEach { board -> board.mark(draw)  }
        }
        return boards
    }

    class Board(val values: List<Int>) {
        private val dim = sqrt(values.size.toDouble()).toInt()
        private val marked = linkedSetOf<Int>()
        var turns = 0
            private set

        fun mark(number: Int) {
            if (!isWinner()) {
                turns += 1
                if (values.contains(number)) marked.add(number)
            }
        }

        fun isWinner(): Boolean = (rows() + cols()).any { it.isComplete() }
        fun score(): Int = values.filter { !marked.contains(it) }.sum() * marked.last()

        private fun cell(row: Int, col: Int): Int = values[row * dim + col]
        private fun row(row: Int): List<Int> = (0 until dim).map { col -> cell(row, col) }
        private fun col(col: Int): List<Int> = (0 until dim).map { row -> cell(row, col) }
        private fun rows(): List<List<Int>> = (0 until dim).map { row -> row(row) }
        private fun cols(): List<List<Int>> = (0 until dim).map { col -> col(col) }

        private fun List<Int>.isComplete(): Boolean = this.all { marked.contains(it) }
    }

    private fun parse(input: String): Pair<List<Int>, List<Board>> {
        val segments = input.split("\n\n")
        val draws = segments.first().split(",").map { it.toInt() }
        val boards = segments.drop(1).map { values ->
            values.lines().flatMap {
                it.split(" ").filter { !it.isEmpty() }.map { it.toInt() }
            }.let { Board(it) }
        }
        return draws to boards
    }
}