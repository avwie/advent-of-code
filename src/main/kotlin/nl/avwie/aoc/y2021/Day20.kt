package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day20 : Day<Int, Int> {

    private val image: Image
    private val algorithm: Set<Int>

    init {
        parse(Input.inputLines(2021, 20).toList()).also {
            image = it.first
            algorithm = it.second
        }
    }

    override fun part1(): Int = enhance(image, algorithm).take(2).last().pixels.size
    override fun part2(): Int = enhance(image, algorithm).take(50).last().pixels.size

    private fun parse(lines: List<String>): Pair<Image, Set<Int>> {
        val enhancement = lines.first().foldIndexed(setOf<Int>()) { i, acc, char -> if (char == '#') acc + i else acc }
        val image = lines.drop(2).foldIndexed(setOf<Vector2D<Int>>()) { r, acc, line ->
            acc + line.mapIndexedNotNull { c, char -> if (char == '#') Vector2D(c, r) else null }
        }.let { Image(it) }
        return image to enhancement
    }

    private fun enhance(image: Image, algorithm: Set<Int>): Sequence<Image> = generateSequence(image) { it.enhance(algorithm) }.drop(1)

    data class Image(val pixels: Set<Vector2D<Int>>, private val version: Int = 0) {
        private val bounds = pixels.bounds()

        fun enhance(algorithm: Set<Int>): Image {
            val fallback = algorithm.contains(0) && !algorithm.contains(511) && version % 2 == 1
            val pixelsToCheck = (bounds.x0 - 3 until bounds.x1 + 2).flatMap { x -> (bounds.y0 - 3 until bounds.y1 + 2).map { y -> Vector2D(x, y) } }
            val acc = mutableSetOf<Vector2D<Int>>()
            pixelsToCheck.forEach { pos ->
                val binary = offsets.map { pos + it }.map {
                    when {
                        it.x >= bounds.x0 && it.x < bounds.x1 && it.y >= bounds.y0 && it.y < bounds.y1 -> pixels.contains(it)
                        else -> fallback
                    }
                }.joinToString("") { if (it) "1" else "0" }.toInt(2)

                if (algorithm.contains(binary)) acc.add(pos)
            }
            return Image(acc, version = version + 1)
        }

        companion object {
            private val offsets = listOf(-1, 0, 1).flatMap { i -> listOf(-1, 0, 1).map { j -> i to j } }.map { (y, x) -> Vector2D(x, y) }
        }
    }
}