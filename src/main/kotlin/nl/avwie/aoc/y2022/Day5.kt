import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.dropUntil
import nl.avwie.aoc.common.transpose

typealias State = List<ArrayDeque<Char>>

object Day5 : Day<String, String> {

    data class Move(val amount: Int, val from: Int, val to: Int) {
        companion object {
            val REGEX = Regex("move (\\d*) from (\\d*) to (\\d*)")
        }
    }

    val input get() = Input.inputLines(2022, 5)

    val initialState get() = input
        .takeWhile { line -> line.isNotEmpty() }
        .map { line ->
            IntProgression
                .fromClosedRange(1, line.length, 4)
                .map { index ->
                    line[index]
                }
        }
        .toList()
        .dropLast(1)
        .transpose()
        .map { list -> list.filter { !it.isWhitespace() } }
        .map { list -> ArrayDeque(list) }

    val moves get() = input
        .dropUntil { line -> line.isEmpty() }
        .map { line -> Input.readRegex(line, Move.REGEX) }
        .map { (_, amount, from, to) -> Move(amount.toInt(), from.toInt(), to.toInt()) }
        .toList()

    override fun part1(): String = initialState
        .also { stacks -> applyMoves(stacks, moves, false) }
        .map { stack -> stack.first() }
        .joinToString("")

    override fun part2(): String = initialState
        .also { stacks -> applyMoves(stacks, moves, true) }
        .map { stack -> stack.first() }
        .joinToString("")

    private fun applyMoves(state: State, moves: Iterable<Move>, shouldBuffer: Boolean) {
        moves.forEach { move ->
            val buffer = ArrayDeque<Char>()
            repeat(move.amount) {
                state[move.from - 1].removeFirst().also { item ->
                    if (shouldBuffer) buffer.addFirst(item)
                    else state[move.to - 1].addFirst(item)
                }
            }
            if (shouldBuffer) {
                buffer.forEach { item -> state[move.to - 1].addFirst(item) }
            }
        }
    }
}