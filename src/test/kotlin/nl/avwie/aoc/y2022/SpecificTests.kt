package nl.avwie.aoc.y2022

import Day15.horizontalEdge
import Day15.isInRange
import nl.avwie.aoc.common.Vector2D
import org.junit.jupiter.api.Test


class SpecificTests {

    val input = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().lineSequence()

    @Test
    fun sensors() {
        val pairs = Day15.parse(input)
        val pair = pairs[6]
        val r = (0 .. 15)
            .map { Vector2D(it, 10) }
            .map { pair.isInRange(it) }

        val edge = pair.horizontalEdge(11)
        val count = Day15.getBlockedCount(pairs, 10)
    }
}