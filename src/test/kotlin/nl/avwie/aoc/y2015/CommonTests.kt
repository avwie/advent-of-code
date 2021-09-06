package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Base
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CommonTests {

    @Test
    fun baseTest() {
        val codex = (0 .. 9).map { it.toString()[0] }
        val zero = Base.zero(10)
        val one = Base.one(10)

        assertEquals("0", zero.toString(codex))
        assertEquals("1", one.toString(codex))
        assertEquals("2", (one + one).toString(codex))
        assertEquals("10", generateSequence(zero) { it + one }.elementAt(10).toString(codex))
        assertEquals("100", generateSequence(zero) { it + one }.elementAt(100).toString(codex))
        assertEquals("12345", generateSequence(zero) { it + one }.elementAt(12345).toString(codex))
        assertEquals("1010", generateSequence(Base.zero(2)) { it + Base.one(2) }.elementAt(10).toString(listOf('0', '1')))

        assertEquals(2, Base.fromString("1010", listOf('0', '1')).base)
        assertEquals(listOf(0, 1, 0, 1), Base.fromString("1010", listOf('0', '1')).values)
    }
}