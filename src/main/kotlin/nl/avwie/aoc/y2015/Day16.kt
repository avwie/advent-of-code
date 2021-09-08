package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day16 : Day<Int, Int> {

    private val sample = """
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
    """.trimIndent()
        .lines().associate { line ->
            line.split(":").let { (key, value) -> key.trim() to value.trim().toInt() }
        }

    override fun part1(): Int = parse(Input.inputLines(2015, 16)).indexOfFirst { input ->
        isMatch(input, sample)
    } + 1

    override fun part2(): Int = parse(Input.inputLines(2015, 16)).indexOfFirst { input ->
        isMatch2(input, sample)
    } + 1

    fun parse(lines: Sequence<String>): List<Map<String, Int>> = lines.map { line ->
        line.dropWhile { it != ':' }.drop(1).split(",").map { pair ->
            val (key, value) = pair.split(':')
            key.trim() to value.trim().toInt()
        }.toMap()
    }.toList()

    fun isMatch(input: Map<String, Int>, sample: Map<String, Int>): Boolean = input.all { (key, value) -> sample[key] == value }

    fun isMatch2(input: Map<String, Int>, sample: Map<String, Int>): Boolean = input.all { (key, value) ->
        when (key) {
            "cats", "trees" -> value > sample[key]!!
            "pomeranians", "goldfish" -> value < sample[key]!!
            else -> sample[key] == value
        }
    }

}