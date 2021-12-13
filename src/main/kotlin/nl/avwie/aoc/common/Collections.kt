package nl.avwie.aoc.common

fun <T> combinations(seed: Iterable<T>, count: Int): List<List<T>> {

    fun inner(acc: List<List<T>>, remaining: Int): List<List<T>> = when (remaining) {
        0 -> acc
        count ->  inner(seed.map { s -> listOf(s) }, remaining - 1)
        else -> inner(seed.flatMap { s -> acc.map { list -> list + s } }, remaining - 1)
    }

    return inner(emptyList(), count)
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (this.size == 1) return listOf(this)

    val perms = mutableListOf<List<T>>()
    val toInsert = this[0]
    this.drop(1).permutations().forEach { perm ->
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}

fun <T> Iterable<T>.mostCommonOrNull(): T? = this
    .groupBy { it }
    .maxByOrNull { (_, l) -> l.size }
    ?.key

fun <T> Sequence<T>.takeWhileInclusive(pred: (T) -> Boolean): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}

fun Iterable<Long>.minMax(): Pair<Long, Long> = this.fold(Long.MAX_VALUE to Long.MIN_VALUE) { (min, max), v ->
    kotlin.math.min(min, v) to kotlin.math.max(max, v)
}

fun List<Long>.median(): Long = this.sorted().let { it[it.size / 2] }
fun List<Int>.median(): Int = this.sorted().let { it[it.size / 2] }

fun Iterable<Vector2D<Int>>.toImage(): String {
    return (0 .. this.maxOf { it.y }).map { y ->
        (0 .. this.maxOf { it.x }).map { x ->
            if (this.contains(Vector2D(x, y))) '#' else '.'
        }.joinToString("")
    }.joinToString("\n")
}