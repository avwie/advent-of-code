package nl.avwie.aoc.y2016

import kotlin.test.Test

class SpecificTests {

    @Test
    fun `Day 1 - Walk`() {
        val cmds = Day1.parse("R8, R4, R4, R8")
        val answer = Day1.firstVisitedTwice(cmds)
        println()
    }
}