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

    @Test
    fun `Day 4 - Part 1`() {
        assertEquals(487, Day4.part1())
    }

    @Test
    fun `Day 4 - Part 2`() {
        assertEquals(849, Day4.part2())
    }

    @Test
    fun `Day 5 - Part 1`() {
        assertEquals("PTWLTDSJV", Day5.part1())
    }

    @Test
    fun `Day 5 - Part 2`() {
        assertEquals("WZMFVGGZP", Day5.part2())
    }

    @Test
    fun `Day 6 - Part 1`() {
        assertEquals(1723, Day6.part1())
    }

    @Test
    fun `Day 6 - Part 2`() {
        assertEquals(3708, Day6.part2())
    }

    @Test
    fun `Day 7 - Part 1`() {
        assertEquals(1644735L, Day7.part1())
    }

    @Test
    fun `Day 7 - Part 2`() {
        assertEquals(1300850L, Day7.part2())
    }

    @Test
    fun `Day 8 - Part 1`() {
        assertEquals(1812, Day8.part1())
    }

    @Test
    fun `Day 8 - Part 2`() {
        assertEquals(315495, Day8.part2())
    }

    @Test
    fun `Day 9 - Part 1`() {
        assertEquals(6023, Day9.part1())
    }

    @Test
    fun `Day 9 - Part 2`() {
        assertEquals(2533, Day9.part2())
    }

    @Test
    fun `Day 10 - Part 1`() {
        assertEquals(13740, Day10.part1())
    }

    @Test
    fun `Day 10 - Part 2`() {
        val expected = """
            #### #  # ###  ###  #### ####  ##  #    
               # #  # #  # #  # #    #    #  # #    
              #  #  # #  # #  # ###  ###  #    #    
             #   #  # ###  ###  #    #    #    #    
            #    #  # #    # #  #    #    #  # #    
            ####  ##  #    #  # #    ####  ##  #### 
        """.trimIndent()
        assertEquals(expected, Day10.part2())
    }

    @Test
    fun `Day 11 - Part 1`() {
        assertEquals(113232, Day11.part1())
    }

    @Test
    fun `Day 11 - Part 2`() {
        assertEquals(29703395016, Day11.part2())
    }

    @Test
    fun `Day 12 - Part 1`() {
        assertEquals(456, Day12.part1())
    }

    @Test
    fun `Day 12 - Part 2`() {
        assertEquals(454, Day12.part2())
    }

    @Test
    fun `Day 13 - Part 1`() {
        assertEquals(5208, Day13.part1())
    }

    @Test
    fun `Day 13 - Part 2`() {
        assertEquals(25792, Day13.part2())
    }

    @Test
    fun `Day 14 - Part 1`() {
        assertEquals(961, Day14.part1())
    }

    @Test
    fun `Day 14 - Part 2`() {
        assertEquals(26375, Day14.part2())
    }

    @Test
    fun `Day 15 - Part 1`() {
        assertEquals(5108096, Day15.part1())
    }

    @Test
    fun `Day 15 - Part 2`() {
        assertEquals(10553942650264L, Day15.part2())
    }
}