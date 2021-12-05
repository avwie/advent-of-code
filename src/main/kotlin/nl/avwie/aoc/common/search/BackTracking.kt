package nl.avwie.aoc.common.search

class BackTracking<T>(
    val mode: Mode,
    val implementation: Implementation<T>
)
{
    enum class Mode {
        DepthFirst,
        BreadthFirst
    }

    fun search(init: T): T? {
        val deque = ArrayDeque<T>()
        add(init, deque)

        while (deque.isNotEmpty() && !implementation.isFound(deque.first())) {
            implementation.next(deque.removeFirst()).forEach { add(it, deque) }
        }
        return deque.firstOrNull()
    }

    private fun add(item: T, deque: ArrayDeque<T>) = when (mode) {
        Mode.DepthFirst -> deque.addFirst(item)
        Mode.BreadthFirst -> deque.addLast(item)
    }
}