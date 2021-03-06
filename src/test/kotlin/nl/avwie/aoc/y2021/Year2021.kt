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

    @Test
    fun `Day 5 - Part 1`() {
        assertEquals(5835, Day5.part1())
    }

    @Test
    fun `Day 5 - Part 2`() {
        assertEquals(17013, Day5.part2())
    }

    @Test
    fun `Day 6 - Part 1`() {
        assertEquals(373378, Day6.part1())
    }

    @Test
    fun `Day 6 - Part 2`() {
        assertEquals(1682576647495, Day6.part2())
    }

    @Test
    fun `Day 7 - Part 1`() {
        assertEquals(337833, Day7.part1())
    }

    @Test
    fun `Day 7 - Part 2`() {
        assertEquals(96678050, Day7.part2())
    }

    @Test
    fun `Day 8 - Part 1`() {
        assertEquals(274, Day8.part1())
    }

    @Test
    fun `Day 8 - Part 2`() {
        assertEquals(1012089, Day8.part2())
    }

    @Test
    fun `Day 9 - Part 1`() {
        assertEquals(486, Day9.part1())
    }

    @Test
    fun `Day 9 - Part 2`() {
        assertEquals(1059300, Day9.part2())
    }

    @Test
    fun `Day 10 - Part 1`() {
        assertEquals(193275, Day10.part1())
    }

    @Test
    fun `Day 10 - Part 2`() {
        assertEquals(2429644557, Day10.part2())
    }

    @Test
    fun `Day 11 - Part 1`() {
        assertEquals(1749, Day11.part1())
    }

    @Test
    fun `Day 11 - Part 2`() {
        assertEquals(285, Day11.part2())
    }

    @Test
    fun `Day 12 - Part 1`() {
        assertEquals(3495, Day12.part1())
    }

    @Test
    fun `Day 12 - Part 2`() {
        assertEquals(94849, Day12.part2())
    }

    @Test
    fun `Day 13 - Part 1`() {
        assertEquals(810, Day13.part1())
    }

    @Test
    fun `Day 13 - Part 2`() {
        val code = """
            #..#.#....###..#..#.###...##..####.###.
            #..#.#....#..#.#..#.#..#.#..#.#....#..#
            ####.#....###..#..#.###..#....###..#..#
            #..#.#....#..#.#..#.#..#.#.##.#....###.
            #..#.#....#..#.#..#.#..#.#..#.#....#.#.
            #..#.####.###...##..###...###.#....#..#
        """.trimIndent()
        assertEquals(code, Day13.part2())
    }

    @Test
    fun `Day 14 - Part 1`() {
        assertEquals(3247, Day14.part1())
    }

    @Test
    fun `Day 14 - Part 2`() {
        assertEquals(4110568157153, Day14.part2())
    }

    @Test
    fun `Day 15 - Part 1`() {
        assertEquals(472, Day15.part1())
    }

    @Test
    fun `Day 15 - Part 2`() {
        assertEquals(2851, Day15.part2())
    }

    @Test
    fun `Day 16 - Part 1`() {
        assertEquals(943, Day16.part1())
    }

    @Test
    fun `Day 16 - Part 2`() {
        assertEquals(167737115857, Day16.part2())
    }

    @Test
    fun `Day 17 - Part 1`() {
        assertEquals(6903, Day17.part1())
    }

    @Test
    fun `Day 17 - Part 2`() {
        assertEquals(2351, Day17.part2())
    }

    @Test
    fun `Day 18 - Part 1`() {
        assertEquals(3793, Day18.part1())
    }

    @Test
    fun `Day 18 - Part 2`() {
        assertEquals(4695, Day18.part2())
    }

    @Test
    fun `Day 19 - Part 1`() {
        assertEquals(400, Day19.part1())
    }

    @Test
    fun `Day 19 - Part 2`() {
        assertEquals(12168, Day19.part2())
    }

    @Test
    fun `Day 20 - Part 1`() {
        assertEquals(5225, Day20.part1())
    }

    @Test
    fun `Day 20 - Part 2`() {
        assertEquals(18131, Day20.part2())
    }

    @Test
    fun `Day 21 - Part 1`() {
        assertEquals(926610, Day21.part1())
    }

    @Test
    fun `Day 21 - Part 2`() {
        assertEquals(146854918035875, Day21.part2())
    }

    @Test
    fun `Day 22 - Part 1`() {
        assertEquals(615700, Day22.part1())
    }

    @Test
    fun `Day 22 - Part 2`() {
        assertEquals(0L, Day22.part2())
    }

    /*@Test
    fun `Day 23 - Part 1`() {
        assertEquals(926610, Day23.part1())
    }

    @Test
    fun `Day 23 - Part 2`() {
        assertEquals(146854918035875, Day23.part2())
    }

    @Test
    fun `Day 24 - Part 1`() {
        assertEquals(926610, Day24.part1())
    }

    @Test
    fun `Day 24 - Part 2`() {
        assertEquals(146854918035875, Day24.part2())
    }*/

    @Test
    fun `Day 25 - Part 1`() {
        assertEquals(926610, Day25.part1())
    }
}