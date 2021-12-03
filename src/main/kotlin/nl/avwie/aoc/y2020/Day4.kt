package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day4 : Day<Long, Long> {

    val requiredKeys = setOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    )

    override fun part1(): Long {
        return Input.inputLines(2020, 4)
            .let(::parse)
            .count { map -> hasKeys(map, requiredKeys) }
            .toLong()
    }

    override fun part2(): Long {
        return Input.inputLines(2020, 4)
            .let(::parse)
            .count { map ->
                hasKeys(map, requiredKeys) && isValid(map, requiredKeys)
            }
            .toLong()
    }

    fun parse(input: Sequence<String>): Sequence<Map<String, String>> = sequence {
        var map = mutableMapOf<String, String>()
        input.forEach { line ->
            if (line.isEmpty()) {
                yield(map)
                map = mutableMapOf()
            } else {
                line
                    .split(' ')
                    .map { pair -> pair.split(':') }
                    .forEach { (key, value) -> map[key] = value }
            }
        }
        yield(map)
    }.filter { it.isNotEmpty() }

    fun hasKeys(map: Map<String, String>, requiredKeys: Set<String>): Boolean = requiredKeys.all { map.containsKey(it) }

    fun isValid(map: Map<String, String>, keys: Set<String>): Boolean = keys.all { key ->  isValidKey(key, map[key]!!) }

    fun isValidKey(key: String, value: String): Boolean = when (key) {
        "byr" -> value.toInt().let { it in 1920..2002 }
        "iyr" -> value.toInt().let { it in 2010..2020 }
        "eyr" -> value.toInt().let { it in 2020..2030 }
        "hgt" -> setOf("cm", "in")
            .firstOrNull { unit -> value.contains(unit) }
            ?.let { unit ->
                val length = (value.replace(unit, "")).toInt()
                when (unit) {
                    "cm" -> length in 150..193
                    "in" -> length in 59..76
                    else -> false
                }
            } ?: false
        "hcl" -> value.matches(Regex("#[a-f0-9]{6}"))
        "ecl" -> setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)
        "pid" -> value.matches(Regex("[0-9]{9}"))
        "cid" -> true
        else -> false
    }
}