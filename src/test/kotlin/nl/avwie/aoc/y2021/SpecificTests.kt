package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.combinations
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

    @Test
    fun `Day 18 - reducing`() {
        val input = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent().lines().map { Day18.parse(it) }.reduce { l, r -> l.plus(r) }
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", input.toString())
        assertEquals(3488, input.magnitude())

        val input2 = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent().lines().map { Day18.parse(it) }.reduce { l, r -> l.plus(r) }
        assertEquals(4140, input2.magnitude())
    }
}