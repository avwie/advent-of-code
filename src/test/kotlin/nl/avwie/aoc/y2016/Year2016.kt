package nl.avwie.aoc.y2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Year2016 {

    @Test
    fun `Day 1 - Part 1`() {
        assertEquals(288, Day1.part1())
    }

    @Test
    fun `Day 1 - Part 2`() {
        assertEquals(111, Day1.part2())
    }

    @Test
    fun `Day 2 - Part 1`() {
        assertEquals("33444", Day2.part1())
    }

    @Test
    fun `Day 2 - Part 2`() {
        assertEquals("446A6", Day2.part2())
    }

    @Test
    fun `Day 4 - Part 1`() {
        assertEquals(158835, Day4.part1())
    }

    @Test
    fun `Day 4 - Part 2`() {
        assertEquals(993, Day4.part2())
    }

}