package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.permutations

object Day13 : Day<Int, Int> {

    private val REGEX = "(\\w*) would (\\w*) (\\d*) happiness units by sitting next to (\\w*).".toRegex()

    override fun part1(): Int = parse(Input.inputLines(2015, 13)).let { (set, map) -> optimal(set, map) }

    override fun part2(): Int = parse(Input.inputLines(2015, 13))
        .let { (set, map) -> (set + "me") to (map + set.map { other -> (other to "me") to 0 } + set.map { other -> ("me" to other) to 0 })}
        .let { (set, map) -> optimal(set, map) }

    fun parse(input: Sequence<String>): Pair<Set<String>, Map<Pair<String, String>, Int>> = input.fold(setOf<String>() to mapOf()) { (set, map), line ->
        REGEX.matchEntire(line)!!.groupValues.drop(1).let { (person, effect, amount, other) ->
            (set + person) to (map + ((person to other) to if (effect == "gain") amount.toInt() else -amount.toInt()))
        }
    }

    fun optimal(persons: Set<String>, map : Map<Pair<String, String>, Int>): Int = persons.toList()
        .permutations()
        .map { seating -> score(seating, map) }
        .maxOrNull()!!

    fun score(seating: List<String>, map: Map<Pair<String, String>, Int>): Int = seating.mapIndexed { pos, person ->
        val (leftPos, rightPos) = (if (pos - 1 < 0) seating.size - 1 else pos - 1) to (pos + 1) % seating.size
        val (left, right) = seating[leftPos] to seating[rightPos]
        map[person to left]!! + map[person to right]!!
    }.sum()
}