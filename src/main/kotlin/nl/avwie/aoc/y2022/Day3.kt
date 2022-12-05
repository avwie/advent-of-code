package nl.avwie.aoc.y2022

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day3 : Day<Int, Int> {

    val rucksacks = Input.inputLines(2022, 3).parse()
    
    override fun part1(): Int = rucksacks
        .map { items -> items.subList(0, items.size / 2) to items.subList(items.size / 2, items.size) }
        .map { (first, second) ->
            first.toSet().intersect(second.toSet()).first()
        }
        .sum()

    override fun part2(): Int = rucksacks
        .windowed(size = 3, step = 3)
        .map { rucksacks -> rucksacks.map { it.toSet() } }
        .map { (first, second, third) ->
            first.intersect(second).intersect(third).first()
        }
        .sum()

    private fun Sequence<String>.parse() =
        map { line ->
            line.map { char -> char.code - if (char.isLowerCase()) 96 else 38  }
        }.toList()
}