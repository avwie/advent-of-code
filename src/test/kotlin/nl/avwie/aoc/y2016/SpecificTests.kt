package nl.avwie.aoc.y2016

import kotlin.test.Test

class SpecificTests {

    @Test
    fun `Day 3 - valid triangles`() {
        val input = "  101  301  501\n  102  302  502\n  103  303  503\n  201  401  601\n  202  402  602\n  203  403  603".lineSequence()

        val parsed = input.map { Day3.parse(it) }.toList()
    }
}