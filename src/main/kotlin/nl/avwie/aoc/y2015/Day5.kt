package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input.inputLines

object Day5 : Day<Int, Int> {

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')

    override fun part1(): Int = combined(
        hasAtLeastNVowels(3),
        hasAtLeastOneLetterNInARow(2),
        withDenySet("ab", "cd", "pq", "xy")
    ).let { predicate ->
        inputLines(2015, 5).filter { predicate(it) }.count()
    }

    override fun part2(): Int  = combined(
        pairOfNonOverlappingLetters,
        oneLetterWithOneBetween
    ).let { predicate ->
        inputLines(2015, 5).filter { predicate(it) }.count()
    }

    fun interface Predicate {
        operator fun invoke(input: String): Boolean
    }

    fun combined(vararg predicates: Predicate) = Predicate { input -> predicates.all { predicate -> predicate(input) } }
    fun hasAtLeastNVowels(n: Int) = Predicate { input -> input.count { c -> vowels.contains(c) } >= n }
    fun hasAtLeastOneLetterNInARow(n: Int) = Predicate { input -> input.windowed(n).any { subset -> subset.toCharArray().distinct().size == 1 }}
    fun withDenySet(vararg denySet: String) = Predicate { input -> denySet.none { deny -> input.contains(deny) }}

    val pairOfNonOverlappingLetters = Predicate { input ->
        input.contains("([a-z][a-z]).*\\1".toRegex())
    }

    val oneLetterWithOneBetween = Predicate { input ->
        input.windowed(3).any { s -> s[0] == s[2] }
    }
}