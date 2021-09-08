package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import java.lang.Integer.min

object Day14 : Day<Int, Int> {

    data class Reindeer(val name: String, val speed: Int, val flyingTime: Int, val restingTime: Int)

    private val REGEX = "(\\w*) can fly (\\d*) km/s for (\\d*) seconds, but then must rest for (\\d*) seconds.".toRegex()

    override fun part1(): Int = parse(Input.inputLines(2015, 14))
        .maxOf { reindeer -> distance(reindeer).take(2503).last() }

    override fun part2(): Int = points(parse(Input.inputLines(2015, 14)).toList()).take(2503).last()

    fun parse(lines: Sequence<String>): Sequence<Reindeer> = lines.map { line ->
        REGEX.matchEntire(line)!!.groupValues.drop(1).let { (name, speed, flyingTime, restingTime) ->
            Reindeer(name, speed.toInt(), flyingTime.toInt(), restingTime.toInt())
        }
    }

    fun distance(reindeer: Reindeer): Sequence<Int> = generateSequence(1) { it + 1 }.map { time ->
        val totalTime = reindeer.flyingTime + reindeer.restingTime
        val fullCyclesDistance = (time / totalTime) * (reindeer.flyingTime * reindeer.speed)
        val remainingCycleDistance = min((time % totalTime), reindeer.flyingTime) * reindeer.speed
        fullCyclesDistance + remainingCycleDistance
    }

    fun points(reindeer: Iterable<Reindeer>): Sequence<Int> = sequence {
        val points = reindeer.associateWith { 0 }.toMutableMap()
        val distances = reindeer.associateWith(::distance).toMutableMap()

        while (true) {
            val currentBoard = distances.map { (reindeer, distance) -> reindeer to distance.first() }.toMap()
            val maxDistance = currentBoard.values.maxOrNull()!!
            currentBoard.filterValues { it == maxDistance }.keys.forEach { leader ->
                points[leader] = points[leader]!! + 1
            }
            yield(points.values.maxOrNull()!!)

            distances.keys.forEach { reindeer ->
                distances[reindeer] = distances[reindeer]!!.drop(1)
            }
        }
    }
}