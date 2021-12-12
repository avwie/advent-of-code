package nl.avwie.aoc.common.search

class DepthFirstSearch<T>(context: Context<T>) : TreeSearch<T>(context) {
    private val deque = ArrayDeque<T>()

    override fun add(item: T) { deque.addFirst(item) }
    override fun remove() = deque.removeFirstOrNull()
    override fun isEmpty() = deque.isEmpty()

    companion object {
        operator fun <T> invoke(found: (T) -> Boolean, children: (T) -> Iterable<T>): TreeSearch<T> {
            val context = object : Context<T> {
                override fun found(item: T): Boolean = found(item)
                override fun children(item: T): Iterable<T> = children(item)
            }
            return DepthFirstSearch(context)
        }
    }
}

fun <T> depthFirstSearch(init: T, found: (T) -> Boolean, children: (T) -> Iterable<T>): Sequence<T> {
    return DepthFirstSearch(found, children).search(init)
}