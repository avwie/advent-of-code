package nl.avwie.aoc.y2016

import nl.avwie.aoc.common.*

object Day7 : Day<Int, Int> {
    val HYPERNET = Regex("\\[(\\w*)]")

    override fun part1(): Int = Input.inputLines(2016, 7).count(::supportsTLS)
    override fun part2(): Int = Input.inputLines(2016, 7).count(::supportsSSL)

    private fun supportsTLS(str: String) = HYPERNET.findAll(str).map { it.groupValues[1] }.none(::hasABBA) && hasABBA(str)
    private fun hasABBA(str: String): Boolean = str.toList().windowed(4).any { (a0, a1, b0, b1) -> a0 != a1 && a0 == b1 && a1 == b0 }

    private fun supportsSSL(str: String): Boolean {
        val hypernets = HYPERNET.findAll(str).toList()
        TODO()
    }

}