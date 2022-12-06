package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day6 : Day<Int, Int> {

    val datastream get() = Input.input(2022, 6)

    override fun part1(): Int = findMarker(datastream, 4)
    override fun part2(): Int = findMarker(datastream, 14)

    fun findMarker(input: String, markerSize: Int) =  input
        .windowed(markerSize)
        .indexOfFirst { section -> section.toSet().size == section.length } + markerSize
}
