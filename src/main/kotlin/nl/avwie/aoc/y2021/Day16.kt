package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import kotlin.math.max
import kotlin.math.min

object Day16 : Day<Int, Long> {

    private val input = Input.input(2021, 16)
    private val result = parseHex(input).first()

    override fun part1(): Int = result.flatten().sumOf { it.version }
    override fun part2(): Long = result.eval()

    private fun parseHex(input: String) = parseBinary(hex2binary(input))
    private fun parseBinary(input: String): List<Packet> {
        val queue = ArrayDeque(input.toList())
        val packets = mutableListOf<Packet>()

        while (queue.any { it == '1' }) {
            packets.add(packet(queue))
        }
        return packets
    }

    private fun packet(queue: ArrayDeque<Char>): Packet {
        val version = queue.munchInt(3)
        val typeId = queue.munchInt(3)
        return when (typeId) {
            4 -> literal(version, queue)
            else -> operator(typeId, version, queue)
        }
    }

    private fun literal(version: Int, queue: ArrayDeque<Char>): Packet.Literal {
        var isLast = false
        var acc = ""
        while (!isLast) {
            isLast = queue.munchInt(1) == 0
            acc += queue.munchString(4)
        }
        return Packet.Literal(version, acc.toLong(2))
    }

    private fun operator(typeId: Int, version: Int, queue: ArrayDeque<Char>): Packet.Operator {
        val packets: List<Packet> = when (queue.munchInt(1)) {
            0 -> parseBinary(queue.munchString(queue.munchInt(15)))
            1 -> (0 until queue.munchInt(11)).map { packet(queue) }
            else -> listOf()
        }
        return Packet.Operator(typeId, version, packets)
    }

    private fun hex2binary(hex: String): String = hex.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")
    private fun ArrayDeque<Char>.munchString(length: Int): String = (0 until length).map { this.removeFirst() }.joinToString("")
    private fun ArrayDeque<Char>.munchInt(length: Int): Int = munchString(length).toInt(2)
    private fun Iterable<Packet>.flatten(): List<Packet> = this.flatMap { it.flatten() }

    sealed interface Packet {
        val version: Int

        fun flatten(): List<Packet>
        fun eval(): Long

        data class Literal(override val version: Int, val value: Long): Packet {
            override fun flatten(): List<Packet> = listOf(this)
            override fun eval(): Long  = value
        }

        data class Operator(val typeId: Int, override val version: Int, val packets: List<Packet>): Packet {
            override fun flatten(): List<Packet> = listOf(this) + packets.flatMap { it.flatten() }
            override fun eval(): Long = when (typeId) {
                0 -> packets.map { it.eval() }.reduce { a, b -> a + b }
                1 -> packets.map { it.eval() }.reduce { a, b -> a * b }
                2 -> packets.map { it.eval() }.reduce { a, b -> min(a, b) }
                3 -> packets.map { it.eval() }.reduce { a, b -> max(a, b) }
                5 -> if (packets[0].eval() > packets[1].eval()) 1 else 0
                6 -> if (packets[0].eval() < packets[1].eval()) 1 else 0
                7 -> if (packets[0].eval() == packets[1].eval()) 1 else 0
                else -> 0
            }
        }
    }
}