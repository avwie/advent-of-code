package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day2 : Day<Int, Int> {

    private val integrated = Input.inputLines(2021, 2)
        .map { line -> line.split(' ') }
        .map { (cmd, amount) -> cmd to amount.toInt() }
        .fold(Triple(0, 0, 0)) { (depth, position, aim), (cmd, amount) ->
            when (cmd) {
                "forward" -> Triple(depth + aim * amount, position + amount, aim)
                "down" -> Triple(depth, position, aim + amount)
                "up" -> Triple(depth, position, aim - amount)
                else -> Triple(depth, position, aim)
            }
        }.toList()

    override fun part1(): Int = integrated.let { (_, position, depth) -> position * depth }
    override fun part2(): Int = integrated.let { (depth, position, _) -> position * depth }

}