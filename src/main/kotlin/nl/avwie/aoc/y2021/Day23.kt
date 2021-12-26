package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day23 : Day<Int, Int> {

    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun search(amphipods: List<Amphipod>): SearchStep {
        /*val initialState = State(amphipods)
        val initialStep = SearchStep(initialState, 0, null)
        val search = dijkstraSearch(
            init = initialStep,
            found = { step -> step.state.isFinished() },
            children = { step -> step.next() },
            cost = { step -> step.energyUsed.toDouble() },
        )
        return search.first()*/
        TODO()
    }

    data class Amphipod(val position: Vector2D<Int>, val targetRoom: Int, val requiredEnergy: Int, val char: Char)

    data class State(val amphipods: List<Amphipod>) {
        override fun toString(): String = (0..4).joinToString("\n") { r ->
            (0..12).map { c ->
                when {
                    r == 0 -> '#'
                    r == 1 && (c == 0 || c == 12) -> '#'
                    r == 2 && c in listOf(0, 1, 11, 12) -> '#'
                    r in (2..3) && c in listOf(2, 4, 6, 8, 10) -> '#'
                    r == 4 && c in 2..10 -> '#'
                    r in (3 .. 4) && (c in (0 .. 1) || c in (11 .. 12)) -> ' '
                    else -> amphipods.firstOrNull { it.position == Vector2D(c - 1, r - 1) }?.char ?: '.'
                }
            }.joinToString("")
        }
    }

    data class SearchStep(val state: State, val energyUsed: Int, val previous: SearchStep?) {
        override fun equals(other: Any?): Boolean {
            return other.hashCode() == this.hashCode()
        }

        override fun hashCode(): Int {
            var result = state.hashCode()
            result = 31 * result + energyUsed
            return result
        }
    }
}