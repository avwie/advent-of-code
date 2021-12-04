package nl.avwie.aoc.y2021

import kotlin.test.Test
import kotlin.test.assertEquals

class Year2021 {

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

    @Test
    fun `Day 3 - Part 1`() {
        assertEquals(4160394, Day3.part1())
    }

    @Test
    fun `Day 3 - Part 2`() {
        assertEquals(4125600, Day3.part2())
    }

    @Test
    fun `Day 4 - Part 1`() {
        assertEquals(51034, Day4.part1())
    }

    @Test
    fun `Day 4 - Part 2`() {
        assertEquals(5434, Day4.part2())
    }
}