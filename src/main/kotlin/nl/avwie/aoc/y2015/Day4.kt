package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.md5
import nl.avwie.aoc.common.toHex
import java.security.MessageDigest

object Day4 : Day<Int, Int> {

    private val SEED = "yzbqklnj"
    private val md5 = MessageDigest.getInstance("MD5")

    override fun part1(): Int = hashes(SEED)
        .withIndex()
        .filter { (_, v) -> v.slice(0 .. 1).all { b -> b.compareTo(0) == 0 } }
        .filter { (_, v) -> v.toHex().startsWith("00000") }
        .first().index + 1

    override fun part2(): Int = hashes(SEED)
        .withIndex()
        .filter { (_, v) -> v.slice(0 .. 2).all { b -> b.compareTo(0) == 0 } }
        .first().index + 1

    fun hashes(seed: String): Sequence<ByteArray> = generateSequence(1) { it + 1 }
        .map { n -> "$seed$n"}
        .map { s -> s.md5() }
}