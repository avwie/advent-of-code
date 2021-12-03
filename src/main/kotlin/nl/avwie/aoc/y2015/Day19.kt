package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.search.BackPropagation

object Day19 : Day<Int, Int> {

    override fun part1(): Int = parse(Input.inputLines(2015, 19)).let { (rules, molecule) -> molecules(rules, molecule) }.size

    override fun part2(): Int = parse(Input.inputLines(2015, 19))
        .let { (rules, molecule) ->
            reconstruct(rules, molecule, "e")
        }

    fun parse(lines: Sequence<String>): Pair<List<Pair<String, String>>, String> = lines.fold(listOf<Pair<String, String>>() to "") { acc, line ->
        if (line.isBlank()) acc
        else {
            val parts = line.split(" => ")
            if (parts.size == 1) acc.first to parts[0]
            else (acc.first + (parts[0] to parts[1])) to acc.second
        }
    }

    fun molecules(rules: List<Pair<String, String>>, input: String): List<String> = rules.flatMap { (key, value) ->
        replacements(input, key, value)
    }.distinct()

    fun reconstruct(rules: List<Pair<String, String>>, start: String, end: String): Int {
        var target = start
        var count = 0
        var shuffledRules = rules

        while (target != end) {
            val temp = target
            shuffledRules.forEach { (a, b) ->
                if (target.contains(b)) {
                    target = target.replaceFirst(b, a)
                    count += 1
                }
            }

            if (temp == target) {
                target = start
                count = 0
                shuffledRules = rules.shuffled()
            }
        }
        return count
    }

    private fun replacements(input: String, search: String, replace: String): List<String> = input
        .windowed(search.length)
        .mapIndexedNotNull { index, s -> if (s == search) index else null }
        .map { index -> input.substring(0, index) + replace + input.drop(index + search.length) }
}