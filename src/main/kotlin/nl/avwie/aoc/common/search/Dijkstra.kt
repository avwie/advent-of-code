package nl.avwie.aoc.common.search

import java.util.*

class Dijkstra<T, I>(val implementation: I) where I : Implementation<T>, I : WithCost<T> {

    private val comparator = Comparator { a: T, b: T ->
        (implementation.cost(a) to implementation.cost(b)).let { (ca, cb) ->
            when {
                ca < cb -> -1
                ca > cb -> 1
                else -> 0
            }
        }
    }

    fun search(init: T): T? {
        val queue = PriorityQueue(comparator)
        queue.add(init)

        while (queue.isNotEmpty() && !implementation.isFound(queue.peek())) {
            implementation.next(queue.remove()).forEach { item -> queue.add(item) }
        }
        return if (queue.isNotEmpty()) queue.peek() else null
    }
}