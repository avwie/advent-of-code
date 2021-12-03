package nl.avwie.aoc.common

fun <T> Iterable<T>.combinations(count: Int) = combinations(this, count)

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