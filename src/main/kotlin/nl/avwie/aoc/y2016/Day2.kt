package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*
import kotlin.math.sqrt

object Day2 : Day<String, String> {

    data class KeyPad(val r: Int, val c: Int, private val digits: String) {
        private val values = digits.map { if (it == '.') null else it.toString() }
        private val dim = sqrt(values.size.toDouble()).toInt()

        val value = when {
            r < 0 || r >= dim || c < 0 || c >= dim -> null
            else -> values.getOrNull(r * dim + c)
        }

        fun up() = validate(copy(r = r - 1))
        fun down() = validate(copy(r = r + 1))
        fun left() = validate(copy(c = c - 1))
        fun right() = validate(copy(c = c + 1))

        private fun validate(other: KeyPad): KeyPad = if (other.value != null) other else this
    }

    val simpleKeyPad = KeyPad(1, 1, "123456789")
    val crazyKeyPad = KeyPad(2, 0, "..1...234.56789.ABC...D..")

    private val instructions = Input.inputLines(2016, 2).toList()

    override fun part1(): String = solve(instructions, simpleKeyPad)
    override fun part2(): String = solve(instructions, crazyKeyPad)

    fun solve(instructions: List<String>, init: KeyPad) = instructions.fold(listOf<KeyPad>()) { history, instruction ->
        history + followInstruction(instruction, history.lastOrNull() ?: init)
    }.map { it.value }.joinToString("")

    private fun followInstruction(instruction: String, init: KeyPad) = instruction.fold(init) { keypad, char ->
        when (char) {
            'U' -> keypad.up()
            'D' -> keypad.down()
            'L' -> keypad.left()
            'R' -> keypad.right()
            else -> keypad
        }
    }
}