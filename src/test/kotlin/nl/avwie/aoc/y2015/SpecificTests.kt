package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.md5
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpecificTests {

    @Test
    fun `Day 1 - Sample inputs`() {
        val assertions = mapOf(
            "(())" to 0,
            "()()" to 0,
            "(((" to 3,
            "(()(()(" to 3,
            "))(((((" to 3,
            "())"  to -1,
            "))(" to -1,
            ")))" to -3,
            ")())())" to -3
        )

        assertions.forEach { (instructions, output) ->
            assertEquals(output, Day1.floor(Day1.instructions(instructions)).last())
        }
    }

    @Test
    fun `Day 4 - Sampe inputs`() {
        val input = "abcdef609043"
        val hashed = input.md5()

        val subset = hashed.slice(0 .. 2).map { it.compareTo(0) }
        println()
    }
}