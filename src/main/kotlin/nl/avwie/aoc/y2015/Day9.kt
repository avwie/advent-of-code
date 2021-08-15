package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.permutations

object Day9 : Day<Int, Int> {

    private val REGEX = "(\\D*) to (\\D*) = (\\d*)".toRegex()

    override fun part1(): Int = parseDistances(Input.inputLines(2015, 9)).let { distances ->
        cities(distances).permutations().map { path ->
            path.windowed(2).fold(0) { acc, (fro, to) -> acc + distances[fro to to]!!}
        }
    }.minOrNull()!!

    override fun part2(): Int = parseDistances(Input.inputLines(2015, 9)).let { distances ->
        cities(distances).permutations().map { path ->
            path.windowed(2).fold(0) { acc, (fro, to) -> acc + distances[fro to to]!!}
        }
    }.maxOrNull()!!

    fun parseDistances(lines: Sequence<String>): Map<Pair<String, String>, Int> = lines
        .map { line -> REGEX.matchEntire(line)!!.groupValues }
        .fold(mapOf()) { distances, (_, from, to, distance) ->
            distances + ((from to to) to distance.toInt()) + ((to to from) to distance.toInt())
        }

    fun cities(distances: Map<Pair<String, String>, Int>): List<String> = distances.keys
        .fold<Pair<String, String>, List<String>>(listOf()) { set, (from, _) ->
            set + from
        }.distinct()
}