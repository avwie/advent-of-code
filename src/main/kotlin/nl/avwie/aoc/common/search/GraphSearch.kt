package nl.avwie.aoc.common.search

abstract class GraphSearch<T>(private val context: Context<T>) {

    abstract fun add(item: T)
    abstract fun remove(): T?
    abstract fun isEmpty(): Boolean
    abstract fun clear()

    open fun search(init: T): Sequence<T> = sequence {
        clear()
        add(init)

        while (!isEmpty()) {
            val next = remove()!!
            val (solutions, children) = context.children(next).partition { context.found(it) }
            solutions.forEach {
                yield(it)
            }
            children.forEach {
                add(it)
            }
        }
    }
}