package nl.avwie.aoc.y2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Year2022 {

    @Test
    fun `Day 1 - Part 1`() {
        assertEquals(71934L, Day1.part1())
    }

    @Test
    fun `Day 1 - Part 2`() {
        assertEquals(211447L, Day1.part2())
    }

    @Test
    fun `Day 2 - Part 1`() {
        assertEquals(13446L, Day2.part1())
    }

    @Test
    fun `Day 2 - Part 2`() {
        assertEquals(13509L, Day2.part2())
    }

    @Test
    fun `Day 3 - Part 1`() {
        assertEquals(7848, Day3.part1())
    }

    @Test
    fun `Day 3 - Part 2`() {
        assertEquals(2616, Day3.part2())
    }
}