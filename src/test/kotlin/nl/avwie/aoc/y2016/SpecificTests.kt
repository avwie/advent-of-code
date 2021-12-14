package nl.avwie.aoc.y2016

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecificTests {

    @Test
    fun checkSum() {
        assertEquals("abxyz", Day4.checksum("aaaaa-bbb-z-y-x-123"))
        assertEquals("abcde", Day4.checksum("a-b-c-d-e-f-g-h-987"))
        assertEquals("oarel", Day4.checksum("not-a-real-room-404"))
    }

    @Test
    fun rotate() {
        val x = Day4.rotate("qzmt-zixmtkozy-ivhz").take(343 % 26).toList()
        println()
    }
}