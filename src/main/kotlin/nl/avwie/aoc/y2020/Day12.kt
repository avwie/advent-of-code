package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.Complex.Companion.UNIT
import nl.avwie.aoc.common.Complex.Companion.ZERO
import nl.avwie.aoc.common.Complex.Companion.j
import kotlin.math.abs

import nl.avwie.aoc.y2020.Day12.State.Companion.ShipMode
import nl.avwie.aoc.y2020.Day12.State.Companion.WaypointMode

object Day12 : Day<Long, Long> {

    private val PATTERN = Regex("([A-Z])(\\d+)")
    private val commands =   Input.inputLines(2020, 12).let(::parse)

    override fun part1(): Long = navigate(commands, State(ZERO, UNIT, ShipMode)).distance
    override fun part2(): Long = navigate(commands, State(ZERO, 10 + j, WaypointMode)).distance

    fun parse(lines: Sequence<String>): List<Pair<String, Int>> =  Input.readLinesRegex(lines, PATTERN)
        .map { (_, command, data) -> command to data.toInt() }
        .toList()

    fun navigate(commands: List<Pair<String, Int>>, init: State): State = commands.fold(init) { state, (command, data) ->
        state.navigate(command, data)
    }

    data class State(val pos: Complex, val waypoint: Complex, val updater: (State, Complex) -> State) {

        val distance = abs(pos.i) + abs(pos.j)

        fun navigate(command: String, data: Int): State = when (command) {
            "N" -> updater(this, 0 + data * j)
            "E" -> updater(this, data + 0 * j)
            "S" -> updater(this, 0 - data * j)
            "W" -> updater(this, 0 * j - data)
            "L" -> copy(waypoint = waypoint.reduceRepeated(data / 90) { it * j})
            "R" -> copy(waypoint = waypoint.reduceRepeated(data / 90) { it * -j})
            "F" -> copy(pos = pos + waypoint * data)
            else -> this
        }

        companion object {
            val ShipMode: (State, Complex) -> State = { state, offset -> state.copy(pos = state.pos + offset) }
            val WaypointMode: (State, Complex) -> State = { state, offset -> state.copy(waypoint = state.waypoint + offset) }
        }
    }
}