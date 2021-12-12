package nl.avwie.aoc.common.search

interface Context<T> {
    fun found(item: T): Boolean
    fun children(item: T): Iterable<T>
}

interface WithCost<T, C>  {
    fun cost(item: T): C
    fun compare(a: C, b: C): Int
    fun sum(c1: C, c2: C): C
}

interface WithHeuristic<T, C> {
    fun heuristic(item: T): C
}