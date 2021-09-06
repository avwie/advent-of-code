package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Base
import nl.avwie.aoc.common.Day

object Day11 : Day<String, String> {

    val codex = ('a' .. 'z').toList()
    val base = codex.size
    val one = Base.one(base)

    private val INPUT = "hxbxwxba"
    private val FILTER = combined(
        increasingSubSetOfLength(3),
        disallow(listOf('i', 'o', 'l'), Base.Alphabet.CODEX),
        nonOverlappingPairs(2)
    )

    private val sequence get() = generateSequence(Base.Alphabet.fromString(INPUT)) { it + Base.Alphabet.ONE }
        .filter(FILTER)
        .map { it.toString(Base.Alphabet.CODEX) }

    override fun part1(): String = sequence.elementAt(0)
    override fun part2(): String = sequence.elementAt(1)

    fun increasingSubSetOfLength(n: Int): (Base) -> Boolean = { v ->
        v.values.reversed().windowed(n).any { window ->
            window.windowed(2).all { (a, b) -> b - a == 1 }
        }
    }

    fun disallow(set: List<Char>, codex: List<Char>): (Base) -> Boolean = { v ->
        val numbers = set.map { codex.indexOf(it) }.toSet()
        v.values.none { numbers.contains(it) }
    }

    fun nonOverlappingPairs(count: Int): (Base) -> Boolean = { v ->
        v.values.fold(listOf<Pair<Int, Int>>()) { acc, value ->
            when {
                acc.isEmpty() -> listOf(value to 1)
                acc.last().first == value -> (acc.last().second + 1).let { count -> acc.dropLast(1) + (value to count) }
                else -> acc + (value to 1)
            }
        }
        .filter { (_, size) -> size == 2 }
        .size >= count
    }

    fun combined(vararg predicates: (Base) -> Boolean): (Base) -> Boolean = { v -> predicates.all { it(v) }}
}