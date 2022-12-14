package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.zipWithLongest

object Day13 : Day<Int, Int> {

    val packets = Input.inputLines(2022, 13).let(::parseInput).toList()

    override fun part1(): Int = packets
        .windowed(size = 2, step = 2)
        .mapIndexed {
                index, (left, right) ->
            (index + 1) to compare(left, right) }
        .filter { (_, result) -> result == -1 }
        .sumOf { (index, _) -> index }

    override fun part2(): Int = (listOf(parsePacket("[[2]]"), parsePacket("[[6]]")) + packets)
        .sortedWith(Data.Comparator)
        .mapIndexed { i, data -> (i + 1) to data.toString() }
        .filter { (_, str) -> str == "[[2]]" || str == "[[6]]" }
        .fold(1) { acc, (index, _) -> acc * index}

    fun parseInput(input: Sequence<String>): Sequence<Data> = input
        .filterNot { it.isBlank() }
        .map { parsePacket(it) }

    fun parsePacket(packet: String): Data {
        val stack = ArrayDeque<MutableList<Data>>()
        var acc: Int? = null

        fun addAcc() {
            acc?.also { stack.first().add(Value(it)) }
            acc = null
        }

        packet.forEach { char ->
            when (char) {
                '[' -> stack.addFirst(mutableListOf())
                ']' -> {
                    addAcc()
                    when {
                        stack.size > 1 -> Array(stack.removeFirst()).also { stack.first().add(it) }
                        else -> {}
                    }
                }
                ',' -> addAcc()
                else -> acc = when (acc) {
                    null -> char.digitToInt()
                    else -> acc!! * 10 + char.digitToInt()
                }
            }
        }
        require(Array(stack.first()).toString() == packet)
        return Array(stack.first())
    }

    fun compare(left: Data?, right: Data?): Int = when {
        left == null && right != null -> -1
        left != null && right == null -> 1

        left is Value && right is Value -> when {
            left.item < right.item -> -1
            left.item > right.item -> 1
            else -> 0
        }

        left is Array && right is Array -> left.items
            .zipWithLongest(right.items)
            //.firstNotNullOfOrNull { (left, right) -> compare(left, right) }
            .map { (left, right) -> compare(left, right) }
            .firstOrNull { it != 0 } ?: 0

        left is Value && right is Array -> compare(Array(listOf(left)), right)
        left is Array && right is Value -> compare(left, Array(listOf(right)))
        else -> throw IllegalStateException()
    }

    sealed interface Data {
        object Comparator : kotlin.Comparator<Data> {
            override fun compare(o1: Data?, o2: Data?): Int = Day13.compare(o1, o2)
        }
    }
    
    data class Array(val items: List<Data>) : Data {
        override fun toString(): String {
            return "[${items.joinToString(",")}]"
        }
    }
    data class Value(val item: Int): Data {
        override fun toString(): String {
            return item.toString()
        }
    }
}