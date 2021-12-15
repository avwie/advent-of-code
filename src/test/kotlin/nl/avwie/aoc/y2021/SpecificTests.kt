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

    @Test
    fun `Day 7 - fuel`() {
        val input = Day7.parse("16,1,2,0,4,2,7,1,2,14")
        assertEquals(37, Day7.linearCost(input, 2))
        assertEquals(41, Day7.linearCost(input, 1))
        assertEquals(39, Day7.linearCost(input, 3))
        assertEquals(71, Day7.linearCost(input, 10))

        assertEquals(206, Day7.polynomialCost(input, 2))
        assertEquals(168, Day7.polynomialCost(input, 5))
    }

    @Test
    fun `Day 9 - lowpoints`() {
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lineSequence()

        val map = Day9.parse(input)
        val lowPoints = Day9.lowPoints(map)
        val risk = lowPoints.sumOf { point -> Day9.risk(map, point) }
        assertEquals(15, risk)
    }

    @Test
    fun `Day 9 - basin`() {
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lineSequence()

        val map = Day9.parse(input)
        val lowPoints = Day9.lowPoints(map)
        val basins = lowPoints.map { lowPoint -> Day9.basin(map, lowPoint) }
        assertEquals(1134, basins.sortedBy { -it.size }.take(3).fold(1) { acc, basin -> acc * basin.size})
    }

    @Test
    fun `Day 15 - lowest risk`() {
        val input = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent().lineSequence()

        val map = Day15.parse(input)
        val result = Day15.lowestRisk(map)
        assertEquals(40, result)
    }
}