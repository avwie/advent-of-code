package nl.avwie.aoc.common.search

abstract class TreeSearch<T>(private val context: Context<T>) {
    interface Context<T> {
        fun found(item: T): Boolean
        fun children(item: T): Iterable<T>
    }

    abstract fun add(item: T)
    abstract fun remove(): T?
    abstract fun isEmpty(): Boolean

    fun search(init: T): Sequence<T> = sequence {
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