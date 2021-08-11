package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.md5
import nl.avwie.aoc.y2015.Day5.combined
import nl.avwie.aoc.y2015.Day5.oneLetterWithOneBetween
import nl.avwie.aoc.y2015.Day5.pairOfNonOverlappingLetters
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    fun `Day 5 - Non overlapping letters`() {
        assertTrue { Day5.pairOfNonOverlappingLetters("xyxy") }
        assertTrue { Day5.pairOfNonOverlappingLetters("aabcdefgaa") }
        assertTrue { Day5.pairOfNonOverlappingLetters("qjhvhtzxzqqjkmpb") }
        assertTrue { Day5.pairOfNonOverlappingLetters("xxyxx") }
        assertTrue { Day5.pairOfNonOverlappingLetters("uurcxstgmygtbstg") }
        assertFalse { Day5.pairOfNonOverlappingLetters("baaab") }
        assertFalse { Day5.pairOfNonOverlappingLetters("ieodomkazucvgmuy") }
    }

    @Test
    fun `Day 5 - One letter with one between`() {
        assertTrue { Day5.oneLetterWithOneBetween("qjhvhtzxzqqjkmpb") }
        assertTrue { Day5.oneLetterWithOneBetween("xxyxx") }
        assertFalse { Day5.oneLetterWithOneBetween("uurcxstgmygtbstg") }
        assertTrue { Day5.oneLetterWithOneBetween("ieodomkazucvgmuy") }
    }

    @Test
    fun `Day 5 - Examples`() {
        val predicate = combined(oneLetterWithOneBetween, pairOfNonOverlappingLetters)
        assertTrue { predicate("qjhvhtzxzqqjkmpb") }
        assertTrue { predicate("xxyxx") }
        assertFalse { predicate("uurcxstgmygtbstg") }
        assertFalse { predicate("ieodomkazucvgmuy") }
    }
}