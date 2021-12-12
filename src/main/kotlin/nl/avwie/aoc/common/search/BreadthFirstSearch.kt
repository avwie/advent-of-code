package nl.avwie.aoc.common.search

class BreadthFirstSearch<T>(context: Context<T>) : GraphSearch<T>(context) {
    private val deque = ArrayDeque<T>()

    override fun add(item: T) { deque.addLast(item) }
    override fun remove(): T? = deque.removeLastOrNull()
    override fun isEmpty(): Boolean = deque.isEmpty()
    override fun clear() { deque.clear() }

    companion object {
        operator fun <T> invoke(found: (T) -> Boolean, children: (T) -> Iterable<T>): GraphSearch<T> {
            val context = object : Context<T> {
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
            }
            return BreadthFirstSearch(context)
        }
    }
}

fun <T> breadthFirstSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>): Sequence<T> {
    return BreadthFirstSearch(found, children).search(init)
}