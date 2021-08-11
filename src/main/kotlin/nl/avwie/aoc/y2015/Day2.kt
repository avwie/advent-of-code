package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day2 : Day<Int, Int> {

    data class Prism(val l: Int, val w: Int, val h: Int) {
        private val sides = listOf(l * w, w * h, h * l)
        private val perimeters = listOf(l + w, w + h, h + l).map { it * 2 }
        val area = sides.sum() * 2
        val volume = w * h * l
        val smallestSide = sides.minOrNull()!!
        val smallestPerimeter = perimeters.minOrNull()!!
    }

    val INPUT get() = Input.inputLines(2015, 2).map(::parseLine)

    override fun part1(): Int = INPUT.map { it.area + it.smallestSide }.sum()

    override fun part2(): Int = INPUT.map { it.smallestPerimeter + it.volume }.sum()

    fun parseLine(line: String) : Prism = line.split("x").map { it.toInt() }.let { (l, w, h) -> Prism(l, w, h) }

}