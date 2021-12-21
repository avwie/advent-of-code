package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day21 : Day<Int, Long> {

    val input = Turn(6, 2, 0, 0, true)

    override fun part1(): Int = Dice()
        .let { dice -> generateSequence(input) { turn -> turn.next(dice.invoke()) } }
        .withIndex()
        .dropWhile { (_, it) -> it.score1 < 1000 && it.score2 < 1000 }
        .first().let { (i, turn) ->  i * 3 * (if (turn.score1 < 1000) turn.score1 else turn.score2)
    }

    override fun part2(): Long = quantum(input, 21)

    fun quantum(init: Turn, target: Int): Long {
        val remaining = CountingSet<Turn>()
        val finished = CountingSet<Turn>()
        val rolls = combinations(listOf(1, 2, 3), 3).map { it.sum() }
        remaining.add(init)

        while (remaining.isNotEmpty()) {
            remaining.items().forEach { (item, count) ->
                remaining.remove(item)

                if (item.score1 >= target || item.score2 >= target) {
                    finished.add(item, count)
                    return@forEach
                }

                rolls.forEach { roll ->
                    remaining.add(item.next(roll), count)
                }
            }
        }
        val (left, right) = finished.items().partition { (turn, _) -> turn.score1 >= 21 }
        return maxOf(left.sumOf { it.second }, right.sumOf { it.second })
    }

    class Dice {
        private var state = 0
        private fun inc(): Int {
            val r = state + 1
            state = r % 100
            return r
        }
        operator fun invoke() = inc() + inc() + inc()
    }

    data class Turn(val pos1: Int, val pos2: Int, val score1: Int, val score2: Int, val turn: Boolean) {
        fun next(roll: Int) = when (turn) {
            true -> ((pos1 + roll - 1) % 10 + 1).let { copy(pos1 = it, score1 = score1 + it, turn = !turn) }
            false ->((pos2 + roll - 1) % 10 + 1).let { copy(pos2 = it, score2 = score2 + it, turn = !turn) }
        }
    }
}