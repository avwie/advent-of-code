package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day10 : Day<Int, String> {

    val ops = Input.inputLines(2022, 10).toList()

    override fun part1(): Int = evaluate(ops)
        .mapIndexed { index, register -> (index + 1) * register }
        .filterIndexed { index, _ -> (index - 19)  % 40 == 0 }
        .sum()

    override fun part2(): String = evaluate(ops)
        .mapIndexed { index, x ->
            when {
                listOf(x -1, x, x + 1).contains(index % 40) -> '#'
                else -> ' '
            }
        }
        .windowed(size = 40, step = 40)
        .joinToString("\n") { line -> line.joinToString("")}

    fun evaluate(operations: Iterable<String>) = sequence {
        var register = 1
        operations.forEach { line ->
            when {
                line.startsWith("noop") -> yield(register)
                line.startsWith("addx") -> line
                    .split(" ")
                    .let { (_, amount) ->
                        yield(register)
                        yield(register)
                        register += amount.toInt()
                    }
            }
        }
    }
}