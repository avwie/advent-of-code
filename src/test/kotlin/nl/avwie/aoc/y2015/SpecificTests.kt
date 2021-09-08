package nl.avwie.aoc.y2015

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser
import nl.avwie.aoc.common.Base
import nl.avwie.aoc.common.permutations
import nl.avwie.aoc.y2015.Day5.combined
import nl.avwie.aoc.y2015.Day5.oneLetterWithOneBetween
import nl.avwie.aoc.y2015.Day5.pairOfNonOverlappingLetters
import org.junit.jupiter.api.Test
import java.io.StringReader
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

    @Test
    fun `Day 7 - Examples`() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent().lineSequence()

        val gates = Day7.gates(input)
        val result = Day7.eval("i", gates, mutableMapOf())
        assertEquals(65079, result)
    }

    @Test
    fun `Day 8 - Unescape`() {
        assertEquals(0, Day8.unescape("\"\"").length)
        assertEquals(1, Day8.unescape("\"\\x27\"").length)
        assertEquals(7, Day8.unescape("\"aaa\\\"aaa\"").length)
    }

    @Test
    fun `Day 8 - Encode`() {
        assertEquals(6, Day8.encode("\"\"").length)
        assertEquals(9, Day8.encode("\"abc\"").length)
        assertEquals(16, Day8.encode("\"aaa\\\"aaa\"").length)
        assertEquals(11, Day8.encode("\"\\x27\"").length)
    }

    @Test
    fun `Day 9 - Parse`() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent().lineSequence()

        val parsed = Day9.parseDistances(input)
        assertEquals(6, parsed.size)

        val cities = Day9.cities(parsed)
        assertEquals(3, cities.size)
    }

    @Test
    fun `Day 10 - next`() {
        val input = "1"
        val results = generateSequence(input, Day10::nextSequence).drop(1).take(5).last()
        println()
    }

    @Test
    fun `Day 11 - predicates`() {
        assertTrue { Day11.increasingSubSetOfLength(3)(Base.Decimal.fromString("123")) }
        assertFalse { Day11.increasingSubSetOfLength(3)(Base.Decimal.fromString("321")) }
        assertFalse { Day11.increasingSubSetOfLength(3)(Base.Decimal.fromString("121212121314")) }

        assertTrue { Day11.disallow(listOf('1', '9'), Base.Decimal.CODEX)(Base.Decimal.fromString("234")) }
        assertFalse { Day11.disallow(listOf('1', '9'), Base.Decimal.CODEX)(Base.Decimal.fromString("1")) }
        assertFalse { Day11.disallow(listOf('1', '9'), Base.Decimal.CODEX)(Base.Decimal.fromString("9")) }
        assertFalse { Day11.disallow(listOf('1', '9'), Base.Decimal.CODEX)(Base.Decimal.fromString("19")) }
        assertFalse { Day11.disallow(listOf('1', '9'), Base.Decimal.CODEX)(Base.Decimal.fromString("23456789")) }

        assertTrue { Day11.nonOverlappingPairs(2)(Base.Decimal.fromString("112344")) }
        assertFalse { Day11.nonOverlappingPairs(2)(Base.Decimal.fromString("11122")) }
        assertFalse { Day11.nonOverlappingPairs(2)(Base.Decimal.fromString("123456")) }

        val combined = Day11.combined(
            Day11.increasingSubSetOfLength(3),
            Day11.disallow(listOf('i', 'o', 'l'), Base.Alphabet.CODEX),
            Day11.nonOverlappingPairs(2)
        )

        val seq1 = generateSequence(Base.Alphabet.fromString("abcdefgh")) { it + Base.Alphabet.ONE }.filter(combined).map { it.toString(Base.Alphabet.CODEX) }
        assertEquals("abcdffaa", seq1.elementAt(0))

        val seq2 = generateSequence(Base.Alphabet.fromString("ghijklmn")) { it + Base.Alphabet.ONE }.filter(combined).map { it.toString(Base.Alphabet.CODEX) }
        assertEquals("ghjaabcc", seq2.elementAt(0))
    }

    @Test
    fun `Day 13 - parse`() {
        val input = """
            Alice would gain 54 happiness units by sitting next to Bob.
            Alice would lose 79 happiness units by sitting next to Carol.
            Alice would lose 2 happiness units by sitting next to David.
            Bob would gain 83 happiness units by sitting next to Alice.
            Bob would lose 7 happiness units by sitting next to Carol.
            Bob would lose 63 happiness units by sitting next to David.
            Carol would lose 62 happiness units by sitting next to Alice.
            Carol would gain 60 happiness units by sitting next to Bob.
            Carol would gain 55 happiness units by sitting next to David.
            David would gain 46 happiness units by sitting next to Alice.
            David would lose 7 happiness units by sitting next to Bob.
            David would gain 41 happiness units by sitting next to Carol.
        """.trimIndent().lineSequence()

        val (set, map) = Day13.parse(input)
        assertEquals(54, map["Alice" to "Bob"])
        assertEquals(-7, map["Bob" to "Carol"])
        assertEquals(4, set.size)
        assertEquals(330, Day13.optimal(set, map))
    }

    @Test
    fun `Day 15 - example`() {
        val butterScotch = Day15.Ingredient("Butterscotch", -1, -2, 6, 3, 8)
        val cinnamon = Day15.Ingredient("Cinnamon", 2, 3, -2, -1, 3)
        val amounts = listOf(44, 56)
        val score = Day15.score(amounts, listOf(butterScotch, cinnamon))
        assertEquals(62842880, score)
    }
}