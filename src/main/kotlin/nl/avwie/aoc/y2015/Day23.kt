package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day23 : Day<Int, Int> {

    val program = Input.inputLines(2015, 23).map(::parse).toList()

    override fun part1(): Int = run(program, State(0, 0, 0)).last().b
    override fun part2(): Int = run(program, State(1, 0, 0)).last().b

    private fun run(program: List<Pair<String, String>>, initialState: State): Sequence<State> = sequence {
        var state = initialState
        while (state.pointer < program.size) {
            yield(state)

            val (cmd, args) = program[state.pointer]
            state = state.update(cmd, args)
        }
    }

    private fun parse(line: String): Pair<String, String> = line.take(3)  to line.drop(4).trim()

    data class State(val a: Int, val b: Int, val pointer: Int) {
        fun update(cmd: String, args: String): State = when (cmd) {
            "hlf" -> copy(a = if (args == "a") a / 2 else a, b = if (args == "b") b / 2 else b)
            "tpl" -> copy(a = if (args == "a") a * 3 else a, b = if (args == "b") b * 3 else b)
            "inc" -> copy(a = if (args == "a") a + 1 else a, b = if (args == "b") b + 1 else b)
            "jmp" -> copy(pointer = pointer + args.toInt() - 1)
            "jie" -> args.split(", ").let { (r, j) ->
                when (r) {
                    "a" -> copy(pointer = if (a % 2 == 0) pointer + j.toInt() - 1 else pointer)
                    "b" -> copy(pointer = if (b % 2 == 0) pointer + j.toInt() - 1 else pointer)
                    else -> this
                }
            }
            "jio" -> args.split(", ").let { (r, j) ->
                when (r) {
                    "a" -> copy(pointer = if (a == 1) pointer + j.toInt() - 1 else pointer)
                    "b" -> copy(pointer = if (b == 1) pointer + j.toInt() - 1 else pointer)
                    else -> this
                }
            }
            else -> TODO()
        }.increasePointer()

        private fun increasePointer() = copy(pointer = pointer +  1)
    }
}