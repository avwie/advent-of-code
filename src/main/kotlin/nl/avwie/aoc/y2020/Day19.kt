package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.product

object Day19 : Day<Long, Long> {

    val input get() =  Input.inputLines(2020, 19)

    override fun part1(): Long = read(input, applyFixes = false, 0)
        .let { (rules, messages) ->
            messages.filter { message -> rules.contains(message) }
        }.size.toLong()

    override fun part2(): Long = read(input, applyFixes = true, 10)
        .let { (rules, messages) ->
            rules.map { Regex(it) } to messages }
        .let { (rules, messages) ->
            messages.count { msg -> rules.any { it.matches(msg) } }
        }.toLong()

    fun read(input: Sequence<String>, applyFixes: Boolean, n: Int = 0): Pair<Set<String>, List<String>> = input
        .let { if (applyFixes) fix(it) else it }
        .let(::parse)
        .let { (rules, messages) ->
            when {
                applyFixes -> rules.flatMap { rule -> (0 until n).map { d-> rule.replace("n", (d + 1).toString()) } }.toSet() to messages
                else -> rules to messages
            }
        }

    fun fix(lines: Sequence<String>): Sequence<String> = lines.map { line ->
        when (line) {
            "8: 42" -> "8: (42)+"
            "11: 42 31" -> "11: (42){n}(31){n}"
            else -> line
        }
    }

    fun parse(lines: Sequence<String>): Pair<Set<String>, List<String>> {
        val rules = mutableMapOf<Int, String>()
        val messages = mutableListOf<String>()
        var section = 0

        lines.forEach { line ->
            when {
                line.isBlank() -> section++
                section == 0 -> line.split(":").also { (index, rule) -> rules[index.toInt()] = rule.trim(' ', '"') }
                section > 0 -> messages.add(line)
            }
        }
        return eval(rules) to messages
    }

    fun eval(rules: Map<Int, String>): Set<String> {

        val digits = Regex("\\d+")

        fun inner(rule: String): List<String> = when {
            rule.contains(" | ") -> rule.split(" | ").flatMap { sub -> inner(sub) }
            rule.length == 1 && rule[0].isLetter() -> listOf(rule[0].toString())
            rule[0] == '(' ->  digits.findAll(rule)
                    .flatMap { it.groupValues }
                    .map { it to inner(rules[it.toInt()]!!) }
                    .fold(rule) { acc, (key, values) ->
                        acc.replace(key, values.joinToString("|"))
                    }.let { listOf(it) }

            else -> rule.split(" ")
                    .map { inner(rules[it.toInt()]!!) }
                    .reduce { la, lb ->
                        product(la, lb) { a, b -> a + b }
                    }
        }

        return inner(rules[0]!!).toSet()
    }

}