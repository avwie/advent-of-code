package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day2 : Day<Long, Long> {
    
    val strategy = Input.inputLines(2022, 2).parse()
    
    override fun part1(): Long = strategy
        .map { (opponent, player) ->
            totalScore(opponent, player)
        }
        .sum().toLong()

    override fun part2(): Long = strategy
        .map { (opponent, player ) ->
            totalScore(opponent, transform(opponent, player))
        }
        .sum().toLong()

    private fun Sequence<String>.parse() =
        map { line ->
            line.split(" ").map { it[0] }
                .map { c ->
                    when (c)  {
                        'X' -> 'A'
                        'Y' -> 'B'
                        'Z' -> 'C'
                        else -> c
                    }
                }
        }.toList()

    private fun totalScore(opponent: Char, player: Char) = result(opponent, player) + value(player)

    private fun result(opponent: Char, player: Char) = when (opponent) {
        player -> 3
        'A' -> if (player == 'C') 0 else 6
        'B' -> if (player == 'A') 0 else 6
        'C' -> if (player == 'B') 0 else 6
        else -> 0
    }

    private fun value(player: Char) = when (player) {
        'A' -> 1
        'B' -> 2
        else -> 3
    }

    private fun transform(opponent: Char, player: Char) = when (player) {
        'A' -> when (opponent) {
            'A' -> 'C'
            'B' -> 'A'
            'C' -> 'B'
            else -> opponent
        }
        'B' -> opponent
        'C' -> when (opponent) {
            'A' -> 'B'
            'B' -> 'C'
            'C' -> 'A'
            else -> player
        }
        else -> player
    }
}