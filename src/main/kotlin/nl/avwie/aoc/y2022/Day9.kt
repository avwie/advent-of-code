package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.Vector2D
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day9 : Day<Int, Int> {

    val moves = Input.inputLines(2022, 9).let(::parse).toList()

    override fun part1(): Int = calculateChain(1, moves)
        .toSet()
        .count()

    override fun part2(): Int = calculateChain(9, moves)
        .toSet()
        .count()

    private fun calculateChain(tailCount: Int, headMoves: List<Vector2D<Int>>) = (0 until tailCount)
        .fold(calculatePositions(Vector2D(0, 0), headMoves)) { headPositions, _ ->
            calculateTailPositions(Vector2D(0, 0), headPositions)
        }

    private fun parse(lines: Sequence<String>) = lines.flatMap { line ->
        line.split(" ").let { (direction, amount) ->
            when (direction) {
                "U" -> Vector2D(x = 0, y = -1)
                "D" -> Vector2D(x = 0, y = 1)
                "L" -> Vector2D(x = -1, y = 0)
                "R" -> Vector2D(x = 1, y = 0)
                else -> Vector2D(x = 0, y = 0)
            }.let { move ->
                buildList {
                    repeat(amount.toInt()) { add(move) }
                }
            }
        }
    }

    private fun calculatePositions(init: Vector2D<Int>, moves: List<Vector2D<Int>>) =
        moves.scan(init) { pos, move -> pos + move }

    private fun calculateTailPositions(init: Vector2D<Int>, headPositions: List<Vector2D<Int>>) =
        headPositions.scan(init) { tail, head -> tail + determineTailMove(head, tail) }

    private fun determineTailMove(head: Vector2D<Int>, tail: Vector2D<Int>): Vector2D<Int> = (head - tail).let { diff ->
        when {
            diff.x.absoluteValue <= 1 && diff.y.absoluteValue <= 1 -> Vector2D(0, 0)
            else -> Vector2D(diff.x.sign, diff.y.sign)
        }
    }


}