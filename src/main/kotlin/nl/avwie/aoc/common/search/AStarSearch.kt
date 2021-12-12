package nl.avwie.aoc.common.search

import java.util.*
import kotlin.Comparator

class AStarSearch<T, C>(private val context: AStarContext<T, C>) : GraphSearch<T>(context){
    interface AStarContext<T, C> : Context<T>, WithCost<T, C>, WithHeuristic<T, C>

    data class Node<T, C>(val item: T, val cost: C)

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
            val context = object : AStarContext<T, C> {
                override fun cost(item: T): C = cost(item)
                override fun heuristic(item: T): C = heuristic(item)
                override fun sum(c1: C, c2: C): C = sum(c1, c2)
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
                override fun compare(a: C, b: C): Int = compare(a, b)
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