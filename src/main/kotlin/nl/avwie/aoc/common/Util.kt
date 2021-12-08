package nl.avwie.aoc.common

fun linearSeq(start: Int, step: Int) = generateSequence(start) { it + step }
fun linearSeq(start: Long, step: Long) = generateSequence(start) { it + step }

val offsets = listOf(-1, 0, 1).flatMap { i -> listOf(-1, 0, 1).map { j -> i to j } }.filter { (i, j) -> !(i == j && i == 0) }
fun ray(dr: Int, dc: Int) = if (!(dr == dc && dr == 0)) generateSequence(dr to dc) { (i, j) -> (i + dr) to (j + dc) } else emptySequence()
val rays = offsets.map { (dr, dc) -> ray(dr, dc) }

fun <T> product(la: List<T>, lb: List<T>, plus: (T, T) -> T): List<T> {
    val acc = mutableListOf<T>()
    la.forEach { a ->
        lb.forEach { b ->
            acc.add(plus(a, b))
        }
    }
    return acc
}