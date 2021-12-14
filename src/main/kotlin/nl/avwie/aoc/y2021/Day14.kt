package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day14 : Day<Long, Long> {

    val input = parse(Input.inputLines(2021, 14).toList())
    val replacements = replace(input.first, input.second)

    override fun part1(): Long = score(replacements.take(10).last())
    override fun part2(): Long = score(replacements.take(40).last())

    fun parse(lines: List<String>): Pair<String, Map<String, String>> = lines.take(1).first() to lines.drop(2).map { line ->
        line.split(" -> ").let { (k, v) -> k to v }
    }.toMap()

    fun score(elements: Map<String, Long>): Long = elements.values.let { it.maxOrNull()!! - it.minOrNull()!! }

    fun replace(polymer: String, rules: Map<String, String>): Sequence<Map<String, Long>> = sequence {
        val current = polymer.windowed(2).groupBy { it }.map { (k, v) -> k to v.size.toLong() }.toMap().toMutableMap()
        val elements = polymer.groupBy { it.toString() }.map { (k, v) -> k to v.size.toLong() }.toMap().toMutableMap()

        while (true) {
            val mods = mutableMapOf<String, Long>()
            rules.forEach { (key, replacement) ->
                if ((current[key] ?: 0) > 0) {
                    val occurrences = current[key]!!

                    // remove the keys
                    mods[key] = (mods[key] ?: 0) - occurrences

                    // add the replacement values
                    val (left, right) = (key[0] + replacement) to (replacement + key[1])
                    mods[left] = (mods[left] ?: 0) + occurrences
                    mods[right] = (mods[right] ?: 0) + occurrences

                    // update the elements
                    elements[replacement] = (elements[replacement] ?: 0) + occurrences
                }
            }

            mods.forEach { (k, v) ->
                current[k] = (current[k] ?: 0) + v
            }
            yield(elements.toMap())
        }
    }
}