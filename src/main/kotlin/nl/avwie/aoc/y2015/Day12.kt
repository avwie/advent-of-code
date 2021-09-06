package nl.avwie.aoc.y2015

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day12 : Day<Int, Int> {

    private val parser = Parser.default()

    override fun part1(): Int = sum(parser.parse(Input.inputStream(2015, 12)) as JsonObject, setOf())
    override fun part2(): Int = sum(parser.parse(Input.inputStream(2015, 12)) as JsonObject, setOf("red"))

    private fun sum(item: Any?, disallowSet: Set<String>): Int = when (item) {
        is String -> 0
        is Int -> item
        is JsonObject -> if (disallowSet.any { item.containsValue(it) }) 0 else item.values.sumOf { sub -> sum(sub, disallowSet) }
        is JsonArray<*> -> item.sumOf { sub -> sum(sub, disallowSet) }
        else -> throw IllegalStateException()
    }
}