package nl.avwie.aoc.y2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AllDays {

    @Test
    fun `Day 1 - Part 1`() {
        assertEquals(280, Day1.part1())
    }

    @Test
    fun `Day 1 - Part 2`() {
        assertEquals(1797, Day1.part2())
    }

    @Test
    fun `Day 2 - Part 1`() {
        assertEquals(1598415, Day2.part1())
    }

    @Test
    fun `Day 2 - Part 2`() {
        assertEquals(3812909, Day2.part2())
    }

    @Test
    fun `Day 3 - Part 1`() {
        assertEquals(2081, Day3.part1())
    }

    @Test
    fun `Day 3 - Part 2`() {
        assertEquals(2341, Day3.part2())
    }

    @Test
    fun `Day 4 - Part 1`() {
        assertEquals(282749, Day4.part1())
    }

    @Test
    fun `Day 4 - Part 2`() {
        assertEquals(9962624, Day4.part2())
    }

    @Test
    fun `Day 5 - Part 1`() {
        assertEquals(255, Day5.part1())
    }

    @Test
    fun `Day 5 - Part 2`() {
        assertEquals(55, Day5.part2())
    }

    @Test
    fun `Day 6 - Part 1`() {
        assertEquals(569999, Day6.part1())
    }

    @Test
    fun `Day 6 - Part 2`() {
        assertEquals(17836115, Day6.part2())
    }
}