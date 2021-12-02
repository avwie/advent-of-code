package nl.avwie.aoc.y2021

import kotlin.test.Test
import kotlin.test.assertEquals

class AllDays {

    @Test
    fun `Day 1 - Part 1`() {
        assertEquals(1288, Day1.part1())
    }

    @Test
    fun `Day 1 - Part 2`() {
        assertEquals(1311, Day1.part2())
    }

    @Test
    fun `Day 2 - Part 1`() {
        assertEquals(1989014, Day2.part1())
    }

    @Test
    fun `Day 2 - Part 2`() {
        assertEquals(2006917119, Day2.part2())
    }
}