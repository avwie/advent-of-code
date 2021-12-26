package nl.avwie.aoc.common.search

import java.util.*

class DijkstraSearch<T, C>(private val context: C) : GraphSearch<T>(context) where C : Context<T>, C : WithCost<T, Double> {

    private val comparator = Comparator { a: T, b: T ->
        (context.cost(a) to context.cost(b)).let { (ca, cb) -> ca.compareTo(cb) }
    }

    private val queue = PriorityQueue(comparator)
    private val visited = mutableSetOf<T>()

    override fun add(item: T) {
        if (visited.contains(item)) return

        queue.add(item)
        visited.add(item)
    }
    override fun remove(): T? = queue.remove()
    override fun isEmpty() = queue.isEmpty()
    override fun clear() {
        visited.clear()
        queue.clear()
    }

    companion object {
        operator fun <T> invoke(found: (T) -> Boolean, children: (T) -> Iterable<T>, cost: (T) -> Double): GraphSearch<T> {
            val context = object : Context<T>, WithCost<T, Double> {
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
                override fun cost(item: T): Double = cost(item)
                override fun sum(c1: Double, c2: Double): Double = c1 + c2
                override fun compare(a: Double, b: Double): Int = a.compareTo(b)
            }
            return DijkstraSearch(context)
        }
    }
}

fun <T> dijkstraSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>, cost: (T) -> Double): Sequence<T> {
    return DijkstraSearch(found, children, cost).search(init)
}