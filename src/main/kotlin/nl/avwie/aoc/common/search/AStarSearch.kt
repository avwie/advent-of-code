package nl.avwie.aoc.common.search

import java.util.*
import kotlin.Comparator

class AStarSearch<T, C>(private val context: Context<T, C>) : GraphSearch<T>(context) {

    data class Node<T, C>(val item: T, val cost: C)

    interface Context<T, C> : GraphSearch.Context<T>, Comparator<C> {
        fun cost(node: T): C
        fun heuristic(node: T): C
        fun sum(a: C, b: C): C
    }
    private val comparator =  Comparator<Node<T, C>> { a, b -> context.compare(a.cost, b.cost) }
    private val queue = PriorityQueue(comparator)
    private val visited = mutableSetOf<T>()

    override fun add(item: T) {
        if (visited.contains(item)) return

        queue.add(Node(item, context.sum(context.cost(item), context.heuristic(item))))
        visited.add(item)
    }

    override fun remove(): T? = queue.remove().item
    override fun isEmpty(): Boolean = queue.isEmpty()
    override fun clear() {
        visited.clear()
        queue.clear()
    }

    companion object {
        operator fun <T, C> invoke(
            found: (T) -> Boolean,
            children: (T) -> Iterable<T>,
            cost: (T) -> C,
            heuristic: (T) -> C,
            compare: (C, C) -> Int,
            sum: (C, C) -> C): GraphSearch<T>
        {
            val context = object : Context<T, C> {
                override fun cost(node: T): C = cost(node)
                override fun heuristic(node: T): C = heuristic(node)
                override fun sum(a: C, b: C): C = sum(a, b)
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
                override fun compare(o1: C, o2: C): Int = compare(o1, o2)
        }
            return AStarSearch(context)
        }

        operator fun <T> invoke(
            found: (T) -> Boolean,
            children: (T) -> Iterable<T>,
            cost: (T) -> Double,
            heuristic: (T) -> Double): GraphSearch<T>
        {
            return AStarSearch(
                found, children, cost, heuristic,
                compare = { o1, o2 -> o1.compareTo(o2) },
                sum = { o1, o2 -> o1 + o2 }
            )
        }
    }
}

fun <T> aStarSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>, cost: (T) -> Double, heuristic: (T) -> Double): Sequence<T> {
    return AStarSearch(found, children, cost, heuristic).search(init)
}