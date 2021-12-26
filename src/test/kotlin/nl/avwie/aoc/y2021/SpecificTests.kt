package nl.avwie.aoc.y2021

import org.junit.jupiter.api.Test

class SpecificTests {

    @Test
    fun herds() {
        val input = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>
        """.trimIndent()

        val herds = Day25.parse(input.lines())
        val movement = Day25.movement(herds).windowed(2).withIndex().first { (_, h) -> h[0] == h[1] }.index + 1
        println()
    }
}