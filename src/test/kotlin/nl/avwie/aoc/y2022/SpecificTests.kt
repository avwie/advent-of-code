package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Vector2D
import nl.avwie.aoc.common.dropUntil
import org.junit.jupiter.api.Test


class SpecificTests {

    val input = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lineSequence()

    @Test
    fun grains() {
        val blocked = Day14.parse(input)
        val floorLevel = blocked.maxOf { it.y + 2 }
        val finalState = Day14.simulate(blocked, Vector2D(500, 0), floorLevel)

        val result = finalState.blocked.size - blocked.size
    }
}