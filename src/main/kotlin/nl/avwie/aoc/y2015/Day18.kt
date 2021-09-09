package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import kotlin.math.max

object Day18 : Day<Int, Int> {

    override fun part1(): Int = parse(Input.inputLines(2015, 18))
        .let { (positions, dims) ->
            animate(positions, dims)
        }
        .take(100)
        .last().size

    override fun part2(): Int = parse(Input.inputLines(2015, 18))
        .let { (positions, dims) ->
            animate(positions, dims) { (rows, cols) ->
                mutableSetOf(0 to 0, rows - 1 to 0, 0 to cols - 1, rows - 1 to cols - 1)
            }
        }
        .take(100)
        .last().size

    fun animate(
        positions: Set<Pair<Int, Int>>,
        dims: Pair<Int, Int>,
        initializer: (Pair<Int, Int>) -> MutableSet<Pair<Int, Int>> = { mutableSetOf() }
    ): Sequence<Set<Pair<Int, Int>>> = sequence {
        var currentState = positions
        var newState = initializer(dims)
        val (rows, cols) = dims
        while (true) {
            (0 until rows).forEach { r ->
                (0 until cols).forEach { c ->
                    val neighbors = neighbors(r to c, dims).filter { (nx, ny) -> currentState.contains(nx to ny) }.size
                    when {
                        currentState.contains(r to c) && neighbors == 2 || neighbors == 3 -> newState.add(r to c)
                        neighbors == 3 -> newState.add(r to c)
                    }
                }
            }
            yield(newState)
            currentState = newState
            newState = initializer(dims)
        }
    }

    fun neighbors(pos: Pair<Int, Int>, dims: Pair<Int, Int>) = listOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)
        .map { (dx, dy) -> (pos.first + dx) to (pos.second + dy) }
        .filter { (x, y) -> x >= 0 && x < dims.first && y >= 0 && y < dims.second }

    fun parse(lines: Sequence<String>): Pair<Set<Pair<Int, Int>>, Pair<Int, Int>> = lines
        .foldIndexed(setOf<Pair<Int, Int>>() to (0 to 0)) { row, (set, dims), line ->
            (set + line.mapIndexedNotNull { col, char -> if (char == '#') row to col else null }) to (max(dims.first, row + 1) to line.length)
        }
}