package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day4 : Day<Int, Int> {

    private val finalBoards = parse(Input.input(2021, 4)).let { (draws, boards) -> play(draws, boards) }

    override fun part1(): Int = finalBoards.filter { it.isWinner }.minByOrNull { it.turns}!!.score
    override fun part2(): Int = finalBoards.filter { it.isWinner }.maxByOrNull { it.turns }!!.score

    private fun play(draws: List<Int>, initialBoards: List<BingoMap>): List<BingoMap> =
        draws.fold(initialBoards) { boards, number ->
            boards.map { board -> board.mark(number) }
        }

    data class BingoMap(override val values: List<Int>, val marked: List<Int>): SquareMap<Int> {
        val isWinner = (rows() + cols()).any { it.isComplete() }
        val score = values.filter { !marked.contains(it) }.sum() * (marked.lastOrNull() ?: 0)
        val turns = marked.size

        fun mark(number: Int) = if (isWinner) this else copy(marked = marked + number)

        private fun List<Int>.isComplete(): Boolean = this.all { marked.contains(it) }
    }

    private fun parse(input: String): Pair<List<Int>, List<BingoMap>> {
        val segments = input.split("\n\n")
        val draws = segments.first().split(",").map { it.toInt() }
        val boards = segments.drop(1).map { block ->
            block.replace("\n", " ")
                .windowed(2, 3)
                .map { it.trim().toInt() }
                .let { BingoMap(it, listOf()) }
        }
        return draws to boards
    }
}