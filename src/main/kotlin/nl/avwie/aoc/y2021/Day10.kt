package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import java.util.*

object Day10 : Day<Long, Long> {

    sealed class Result(val score: Long)
    data class Syntax(val char: Char):
        Result(score = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)[char]!!)

    data class AutoComplete(val chars: List<Char>):
        Result(score = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4).let { map ->
            chars.fold(0L) { acc, c -> acc * 5L + map[c]!!}
        })

    private val results = Input.inputLines(2021, 10).map(::analyze).toList()

    override fun part1(): Long = results.filterIsInstance<Syntax>().sumOf { it.score }
    override fun part2(): Long = results.filterIsInstance<AutoComplete>().map { it.score }.median()

    fun analyze(line: String): Result {
        val match = listOf('(' to ')', '{' to '}', '[' to ']', '<' to '>').toMap()
        val stack = Stack<Char>()
        stack.push(line.first())
        line.drop(1).forEach { char ->
            when {
                match.keys.contains(char) -> stack.push(char)
                match[stack.peek()] == char -> stack.pop()
                else -> return Syntax(char)
            }
        }
        return AutoComplete(stack.map { match[it]!! }.reversed())
    }
}