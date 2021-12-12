package nl.avwie.aoc.common.search

class BreadthFirstSearch<T>(context: Context<T>) : TreeSearch<T>(context) {
    private val deque = ArrayDeque<T>()

    override fun add(item: T) {
        deque.addLast(item)
    }

    override fun remove(): T? {
        return deque.removeLastOrNull()
    }

    override fun isEmpty(): Boolean {
        return deque.isEmpty()
    }
}

fun <T> breadthFirstSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>): Sequence<T> {
    val context = object : TreeSearch.Context<T> {
        override fun found(item: T): Boolean = found(item)
        override fun children(item: T): Iterable<T> = children(item)
    }
    return BreadthFirstSearch(context).search(init)
}