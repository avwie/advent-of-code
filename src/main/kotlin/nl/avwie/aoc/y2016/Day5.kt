package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*

object Day5 : Day<String, String> {

    val ZERO = 0X00.toByte()
    val CUTOFF = 0X10.toByte()

    override fun part1(): String = generateCandidate("reyedfim")
        .map { it[5] }.take(8).joinToString("")

    override fun part2(): String = generateCandidate("reyedfim")
        .filter { it[5].isDigit() && it[5].digitToInt() < 8 }
        .map { it[5].digitToInt() to it[6] }
        .scan(arrayOfNulls<Char>(8)) { arr, (p, c) ->
            if (arr[p] == null) arr[p] = c
            arr
        }.takeWhile { arr -> arr.any { it == null } }.last().joinToString("")

    private fun generateCandidate(seed: String) = linearSeq(0L, 1L)
        .map { index -> seed + index }
        .map { input -> input.md5() }
        .filter { md5 -> md5[0] == ZERO && md5[1] == ZERO && md5[2] in 0..15 }
        .map { md5 -> md5.toHex() }
}