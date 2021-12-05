package nl.avwie.aoc.common.search

interface Implementation<T> {
    fun isFound(item: T): Boolean
    fun next(item: T): Iterable<T>
}

interface WithCost<T> {
    fun cost(item: T): Double
}