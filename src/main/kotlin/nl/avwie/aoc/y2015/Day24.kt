package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.*
import java.util.*

object Day24 : Day<Long, Long> {

    private val input = Input.inputLines(2015, 24).map { it.toInt() }.toList()

    override fun part1(): Long = (input.sum() / 3).let { target ->
        val groups = search(target, input, true).toList()
        groups.minOf { it.QE }
    }

    override fun part2(): Long = (input.sum() / 4).let { target ->
        val groups = search(target, input, true).toList()
        groups.minOf { it.QE }
    }

    fun search(target: Int, options: List<Int>, smallest: Boolean = false): Sequence<Group> = sequence {
        val comparator = Comparator { o1: Group, o2: Group -> o1.remaining.compareTo(o2.remaining) }
        val remaining = PriorityQueue(comparator)
        var smallestResult = Int.MAX_VALUE

        remaining.add(Group(setOf(), target, options.sortedBy { -it }))
        while (remaining.isNotEmpty()) {
            val group = remaining.remove()
            when {
                group.finished -> {
                    smallestResult = minOf(group.items.size, smallestResult)
                    yield(group)
                }
                smallest && group.items.size >= smallestResult -> {}
                group.isValid -> remaining.addAll(group.next())
            }
        }
    }

    data class Group(val items: Set<Int>, val target: Int, val options: List<Int>) {
        val remaining = target - items.sum()
        val validOptions = options.filter { it <= remaining }
        val finished: Boolean = remaining == 0
        val isValid: Boolean = remaining >= 0 && validOptions.isNotEmpty()
        val QE: Long = items.fold(1L) { l, r -> l * r }

        fun next(): List<Group> = when {
            finished -> listOf(this)
            !isValid -> listOf()
            else -> validOptions.mapIndexed { i, option ->
                Group(items + option, target, validOptions.drop(i +  1))
            }
        }
    }
}