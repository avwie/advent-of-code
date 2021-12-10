package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day16 : Day<Long, Long> {

    val input = parse(Input.inputLines(2020, 16))

    override fun part1(): Long = input.let { (fields, _, others) ->
        errorRate(others, fields)
    }.toLong()

    override fun part2(): Long = input.let { (fields, ticket, others) ->
        val valids = others.filter { other -> validate(other, fields).isEmpty() }
        val orderedFields = fieldOrder(fields, valids)
        orderedFields.withIndex()
            .filter { (_, f) -> f.label.startsWith("departure") }
            .map { (i, _) -> ticket[i].toLong() }
            .reduce { acc, i -> acc * i }
    }

    fun parse(lines: Sequence<String>): Triple<List<Field>, List<Int>, List<List<Int>>> {
        val fields = mutableListOf<Field>()
        val tickets = mutableListOf<List<Int>>()
        var section = 0

        lines.forEach { line ->
            when {
                line.isEmpty() -> section++
                section == 0 -> fields.add(Field.fromString(line))
                section > 0 && line[0].isDigit() -> line.split(",").map { it.toInt() }.also { tickets.add(it) }
            }
        }
        return Triple(fields, tickets.first(), tickets.drop(1))
    }

    fun validate(ticket: List<Int>, fields: List<Field>): List<Int> = ticket.filter { d -> fields.all { f -> !f.isValid(d) } }

    fun errorRate(tickets: List<List<Int>>, fields: List<Field>): Int = tickets.flatMap { other -> validate(other, fields) }.sum()

    fun fieldOrder(fields: List<Field>, tickets: List<List<Int>>): List<Field> {

        fun reduce(i: Int, options: Array<List<Field>>) {
            (options.indices).forEach { j ->
                if (i != j) {
                    val oldSize = options[j].size
                    options[j] = options[j].filter { it != options[i][0] }
                    if (options[j].size == 1 && oldSize != 1) {
                        reduce(j, options)
                    }
                }
            }
        }

        val options =  Array(fields.size) { fields }
        tickets.forEach { ticket ->
            ticket.forEachIndexed { i, d ->
                options[i] = options[i].filter { field -> field.isValid(d) }
                if (options[i].size == 1) reduce(i, options)
            }
        }
        return options.map { fs -> fs.first() }
    }

    data class Field(val label: String, val rangeA: IntRange, val rangeB: IntRange) {

        fun isValid(input: Int) = rangeA.contains(input) || rangeB.contains(input)

        companion object {
            val REGEX = Regex("(\\D*): (\\d*)-(\\d*) or (\\d*)-(\\d*)")

            fun fromString(input: String): Field = REGEX.matchEntire(input)!!.groupValues.let { matches ->
                return Field(matches[1], IntRange(matches[2].toInt(), matches[3].toInt()), IntRange(matches[4].toInt(), matches[5].toInt()))
            }
        }
    }
}