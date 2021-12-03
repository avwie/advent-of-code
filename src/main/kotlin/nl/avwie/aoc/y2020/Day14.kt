package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.y2020.Day14.Command.Companion.fromString

object Day14 : Day<Long, Long> {

    val input = Input.inputLines(2020, 14).map(::fromString).toList()

    override fun part1(): Long = evaluate(input, 1)
    override fun part2(): Long = evaluate(input, 2)

    fun parse(input: Sequence<String>) = input.map { Command.fromString(it) }

    fun evaluate(commands: List<Command>, version: Int): Long {
        return commands.fold(Command.Mask("X".repeat(36)) to mutableMapOf<Long, Long>()) { (mask, memory), command ->
            update(command, mask, memory, version)
        }.second.values.sum()
    }

    fun update(command: Command, mask: Command.Mask, memory: MutableMap<Long, Long>, version: Int): Pair<Command.Mask, MutableMap<Long, Long>> {
        return when (command) {
            is Command.Mask -> command to memory
            is Command.Write -> when (version) {
                1 -> mask to memory.also { it[command.address] = command.value and mask.clear or mask.set}
                2 -> mask to memory.also {
                    addresses(maskAddress(command.address, mask)).forEach { address ->
                        it[address] = command.value
                    }
                }
                else -> mask to memory
            }
        }
    }

    fun addresses(mask: Command.Mask): List<Long> = mask.mask.fold(listOf(0)) { list, b ->
        when (b) {
            'X' -> list.map { it * 2 } + list.map { it * 2 + 1 }
            '1' -> list.map { it * 2 + 1 }
            else -> list.map { it * 2 }
        }
    }

    fun maskAddress(address: Long, mask: Command.Mask) = address.toString(2).padStart(36, '0').let { bits ->
        bits.zip(mask.mask).map { (i, m) ->
        when (m) {
            '1', 'X' -> m
            else -> i
        }
    }.joinToString("").let { Command.Mask(it) }
}

    sealed class Command {
        companion object {
            val MASK = Regex("mask = ([X,1,0]{36})")
            val WRITE = Regex("mem\\[(\\d*)\\] = (\\d*)")

            fun fromString(input: String): Command = when {
                input.startsWith("mask") -> MASK.matchEntire(input)!!.groupValues.let { (_, m) -> Mask(m) }
                input.startsWith("mem") -> WRITE.matchEntire(input)!!.groupValues.let { (_, address, value) -> Write(address.toLong(), value.toLong()) }
                else -> error("Invalid input")
            }
        }

        data class Mask(val mask: String) : Command() {
            val clear = mask.map { if (it == 'X') '1' else '0' }.joinToString("").toLong(2)
            val set = mask.map { if (it == 'X') '0' else it }.joinToString("").toLong(2)
        }

        data class Write(val address: Long, val value: Long): Command()
    }
}