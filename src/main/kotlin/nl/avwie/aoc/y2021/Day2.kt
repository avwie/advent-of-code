package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day2 : Day<Int, Int> {

    private val commands = Input.inputLines(2021, 2)
        .map { line -> line.split(' ') }
        .map { (cmd, amount) -> cmd to amount.toInt() }
        .toList()

    override fun part1(): Int = commands
        .fold(0 to 0) { (depth, position), (cmd, amount) ->
            when (cmd) {
                "forward" -> depth to position + amount
                "down" -> (depth + amount) to position
                "up" -> (depth - amount) to position
                else -> depth to position
            }
        }
        .let { (position, depth) -> position * depth }

    override fun part2(): Int = commands
        .fold(Triple(0, 0, 0)) { (depth, position, aim), (cmd, amount) ->
            when (cmd) {
                "forward" -> Triple(depth + aim * amount, position + amount, aim)
                "down" -> Triple(depth, position, aim + amount)
                "up" -> Triple(depth, position, aim - amount)
                else -> Triple(depth, position, aim)
            }
        }
        .let { (position, depth, _) -> position * depth }

}