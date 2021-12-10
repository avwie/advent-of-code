package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.takeWhileInclusive

object Day8 : Day<Long, Long> {
    val program get() = Input.inputLines(2020, 8).let(::parse)

    override fun part1(): Long = program.let(::run).takeWhile { (_, loop) -> !loop }.last().first.toLong()

    override fun part2(): Long = program.let(::fixes).map { program ->
            run(program)
                .takeWhileInclusive { (_, loop) -> !loop }
                .last()
        }.first { (_, loop) -> !loop }.first.toLong()

    fun parse(input: Sequence<String>) = input.map { line ->
        line.split(" ").let { (op, data) -> op to data.toInt() }
    }.toList()

    fun fixes(program: List<Pair<String, Int>>): Sequence<List<Pair<String, Int>>> = program
        .mapIndexedNotNull { line, (op, _) -> when (op){
            "jmp" -> line to "nop"
            "nop" -> line to "jmp"
            else -> null
        } }
        .asSequence()
        .map { (line, op) ->
            program.subList(0, line) + (op to program[line].second) + program.subList(line + 1, program.size)
        }

    fun run(program: List<Pair<String, Int>>): Sequence<Pair<Int, Boolean>> = sequence {
        var instructionPointer = 0
        var acc = 0
        val visited = mutableSetOf<Int>()

        while (instructionPointer < program.size) {
            if (visited.contains(instructionPointer)) {
                yield(acc to true)
                break
            }
            visited.add(instructionPointer)
            program[instructionPointer].also { (op, data) ->
                when (op) {
                    "acc" -> { instructionPointer++; acc += data }
                    "jmp" -> { instructionPointer += data }
                    else -> instructionPointer++
                }
            }
            yield(acc to false)
        }
    }
}