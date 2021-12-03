package nl.avwie.aoc.y2021

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecificTests {

    @Test
    fun `Day 3 - Filter by key`() {
        val lines = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().lines()

        val oxygen = Day3.filterByKey(lines, '1').toInt(2)
        val co2 = Day3.filterByKey(lines, '0').toInt(2)
        assertEquals(230, co2 * oxygen)
    }
}