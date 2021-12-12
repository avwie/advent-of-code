package nl.avwie.aoc.common.search

import java.util.*

class DijkstraSearch<T, C>(context: C) : TreeSearch<T>(context) where C : TreeSearch.Context<T>, C : TreeSearch.WithCost<T> {

    private val comparator = Comparator { a: T, b: T ->
        (context.cost(a) to context.cost(b)).let { (ca, cb) -> ca.compareTo(cb) }
    }

    private val queue = PriorityQueue(comparator)

    override fun add(item: T) { queue.add(item) }
    override fun remove(): T? = queue.remove()
    override fun isEmpty() = queue.isEmpty()

    companion object {
        operator fun <T> invoke(found: (T) -> Boolean, children: (T) -> Iterable<T>, cost: (T) -> Double): TreeSearch<T> {
            val context = object : Context<T>, WithCost<T> {
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
                override fun cost(item: T): Double = cost(item)
            }
            return DijkstraSearch(context)
        }
    }
}

fun <T> dijkstraSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>, cost: (T) -> Double): Sequence<T> {
    return DijkstraSearch(found, children, cost).search(init)
}