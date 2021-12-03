package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day7 : Day<Long, Long> {
    override fun part1(): Long = Input.inputLines(2020, 7)
        .map(::parse)
        .let(::lookupTable)
        .let { table ->
            findBagsContaining("shiny gold", table)
        }.size.toLong()

    override fun part2(): Long = Input.inputLines(2020, 7)
    .map(::parse)
    .let(::lookupTable)
    .let { table ->
        contentCount("shiny gold", table)
    }

    fun parse(line: String): Rule = line.split("contain").let { (bag, c) ->
        val bagColor = color(bag)
        val contents = c.split(", ").mapNotNull(::amountAndColor)
        Rule(bagColor, contents)
    }

    fun lookupTable(rules: Sequence<Rule>): Map<String, Rule> = rules.fold(mapOf()) { map, rule ->
        map + (rule.bagColor to rule)
    }

    fun findBagsContaining(color: String, lookup: Map<String, Rule>): List<Rule> {
        return lookup.values.filter { rule -> containsColor(rule, color, lookup) }
    }

    fun contentCount(color: String, lookup: Map<String, Rule>): Long = lookup[color]!!.contents
        .map { (count, color) ->
            count * (1 + contentCount(color, lookup))
        }.sum()

    fun containsColor(rule: Rule, color: String, lookup: Map<String, Rule>): Boolean {
        return rule.contents.any { it.second == color || containsColor(lookup[it.second]!!, color, lookup) }
    }

    fun amountAndColor(str: String): Pair<Long, String>? = str.trim().split(" ").let { parts ->
        when (parts.size) {
            4 -> parts[0].toLong() to parts[1] + " " + parts[2]
            else -> null
        }
    }

    fun color(str: String): String = str.split(" ").take(2).joinToString(" ")

    data class Rule(val bagColor: String, val contents: List<Pair<Long, String>>)

}