package nl.avwie.aoc.y2021

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecificTests {

    @Test
    fun `Day 18 - explode`() {
        val p1 = Day18.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        p1.explode()
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", p1.toString())
    }

    @Test
    fun `Day 18 - explode 2`() {
        val p1 = Day18.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        p1.flatten().first { it.explode() }
        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", p1.toString())
    }

    @Test
    fun `Day 18 - split`() {
        val p1 = Day18.parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
        p1.explode()
        p1.explode()
        p1.split()
        p1.split()
        p1.explode()
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", p1.toString())
    }
}