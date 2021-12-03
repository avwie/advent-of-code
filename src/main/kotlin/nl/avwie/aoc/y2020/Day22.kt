package nl.avwie.aoc.y2020

import java.util.*
import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day22 : Day<Int, Int> {

    val input get() = Input.inputLines(2020, 22).let(::parse)

    override fun part1(): Int = input.let { (p1, p2) -> play(p1, p2, false).second }.let(::score)
    override fun part2(): Int = input.let { (p1, p2) -> play(p1, p2, true).second }.let(::score)

    fun parse(lines: Sequence<String>): List<Deque<Int>> = lines.fold(mutableListOf()) { deques, line ->
        when  {
            line.startsWith("Player") -> deques.also { it.add(ArrayDeque()) }
            line.isBlank() -> deques
            else -> deques.also {  it.last().addLast(line.toInt()) }
        }
    }

    fun play(p1: Deque<Int>, p2: Deque<Int>, recursive: Boolean): Pair<Int, Deque<Int>> {
        val history = hashSetOf<List<Int>>()
        while (p1.isNotEmpty() && p2.isNotEmpty()) {
            if (recursive) {
                val (keyA, keyB) = p1.toList() to p2.toList()
                if (history.contains(keyA) || history.contains(keyB)) return 1 to p1
                else {
                    history.add(keyA)
                    history.add(keyB)
                }
            }

            val (a, b) = p1.removeFirst() to p2.removeFirst()
            val winner = when {
                recursive && p1.size >= a && p2.size >= b -> play(ArrayDeque(p1.take(a)), ArrayDeque(p2.take(b)), recursive).first
                else -> if (a > b) 1 else 2
            }

            when (winner) {
                1 -> { p1.addLast(a); p1.addLast(b) }
                2 -> { p2.addLast(b); p2.addLast(a) }
            }
        }
        return if (p1.isNotEmpty()) 1 to p1 else 2 to p2
    }
    
    fun score(d: Deque<Int>): Int = d.mapIndexed { i, l -> (d.size - i) * l }.sum()
}