package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day9 : Day<Int, Int> {

    private val REGEX = "(\\D*) to (\\D*) = (\\d*)".toRegex()

    override fun part1(): Int = parseDistances(Input.inputLines(2015, 9)).let { distances ->
        pathLengths(cities(distances), distances)
    }.minOrNull()!!

    override fun part2(): Int = parseDistances(Input.inputLines(2015, 9)).let { distances ->
        pathLengths(cities(distances), distances)
    }.maxOrNull()!!

    fun parseDistances(lines: Sequence<String>): Map<Pair<String, String>, Int> = lines
        .map { line -> REGEX.matchEntire(line)!!.groupValues }
        .fold(mapOf()) { distances, (_, from, to, distance) ->
            distances + ((from to to) to distance.toInt()) + ((to to from) to distance.toInt())
        }

    fun cities(distances: Map<Pair<String, String>, Int>): Set<String> = distances.keys
        .fold(setOf()) { set, (from, _) ->
            set + from
        }

    fun pathLengths(cities: Set<String>, distances: Map<Pair<String, String>, Int>): List<Int> {
        fun inner(from: String, remainingCities: Set<String>, acc: Int): List<Int> {
            if (remainingCities.isEmpty()) return listOf(acc)

            return remainingCities.flatMap { neighbor ->
                val distance = distances[from to neighbor]!! + acc
                inner(neighbor, remainingCities - neighbor, distance)
            }
        }

        return cities.flatMap { city -> inner(city, cities - city, 0) }
    }
}