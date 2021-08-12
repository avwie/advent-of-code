package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day6 : Day<Int, Int> {

    val REGEX = "(\\D*)(\\d*),(\\d*)\\D*(\\d*),(\\d*)".toRegex()

    enum class InstructionType {
        TurnOn,
        TurnOff,
        Toggle;
    }

    data class Instruction(val type: InstructionType, val from: Pair<Int, Int>, val to: Pair<Int, Int>)

    override fun part1(): Int = Input.inputLines(2015, 6)
        .map(::parse)
        .fold(IntArray(1_000_000)) { grid, instr ->
            execute(instr, grid, mapOf(
                InstructionType.TurnOn to { 1 },
                InstructionType.TurnOff to { 0 },
                InstructionType.Toggle to { if (it == 0) 1 else 0 },
            ))
        }.count { it > 0 }

    override fun part2(): Int = Input.inputLines(2015, 6)
        .map(::parse)
        .fold(IntArray(1_000_000)) { grid, instr ->
            execute(instr, grid, mapOf(
                InstructionType.TurnOn to { it + 1 },
                InstructionType.TurnOff to { maxOf(0, it - 1) },
                InstructionType.Toggle to { it + 2 },
            ))
        }.sum()

    fun parse(str: String): Instruction {
        val (cmd, x0, y0, x1, y1) = REGEX.matchEntire(str)!!.groupValues.drop(1)
        val type = when (cmd.trim()) {
            "turn on" -> InstructionType.TurnOn
            "turn off" -> InstructionType.TurnOff
            else -> InstructionType.Toggle
        }
        return Instruction(type, x0.toInt() to y0.toInt(), x1.toInt() to y1.toInt())
    }

    fun execute(instruction: Instruction, grid: IntArray, update: Map<InstructionType, (Int) -> Int>): IntArray {
        val (x0, y0) = instruction.from
        val (x1, y1) = instruction.to
        (x0 .. x1).forEach { x ->
            (y0..y1).forEach { y ->
                val i = y * 1000 + x
                grid[i] = update[instruction.type]!!.invoke(grid[i])
            }
        }
        return grid
    }
}