package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*


object Day17 : Day<Int, Int> {

    private val REGEX = Regex("target area: x=(\\-?\\d*)..(\\-?\\d*), y=(\\-?\\d*)..(\\-?\\d*)")
    private val target = Input.inputRegex(2021, 17, REGEX).let { (_, x0, x1, y0, y1) ->
        Rectangle(x0.toInt(), y0.toInt(), x1.toInt() - x0.toInt(), y1.toInt() - y0.toInt(), Int32)
    }

    override fun part1(): Int = search(target).map { trajectory -> trajectory.maxOf { it.position.y } }.maxOrNull()!!
    override fun part2(): Int = search(target).count()

    data class Probe(val position: Vector2D<Int> = Vector2D(0, 0), val velocity: Vector2D<Int>) {
        fun isValid(target: Rectangle<Int>) = position.y >= target.y0
        fun inTarget(target: Rectangle<Int>) = target.contains(position)
    }

    fun launch(probe: Probe): Sequence<Probe> = sequence {
        var currentPosition = probe.position
        var currentVelocity = probe.velocity
        while (true) {
            yield(Probe(currentPosition, currentVelocity))
            currentPosition += currentVelocity
            val drag = when {
                currentVelocity.x > 0 -> -1
                currentVelocity.x < 0 -> 1
                else -> 0
            }
            currentVelocity += Vector2D(drag, -1)
        }
    }

    fun search(target: Rectangle<Int>): Sequence<Sequence<Probe>> {
        val options = (1 .. target.x1).asSequence().flatMap { x ->
            (target.y0 .. -target.y0).asSequence().map { y -> Vector2D(x, y) }
        }

        return options
            .map { option -> Probe(velocity = option) }
            .map { probe -> launch(probe).takeWhile { p -> p.isValid(target) } }
            .filter { trajectory -> trajectory.any { probe -> probe.inTarget(target)  } }
    }
}