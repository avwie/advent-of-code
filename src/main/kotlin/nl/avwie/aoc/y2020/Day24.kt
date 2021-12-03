package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day24 : Day<Long, Long> {

    val directions = setOf("e", "se", "sw", "w", "nw", "ne")
    val input = flip(Input.inputLines(2020, 24).let(::parse))

    override fun part1(): Long = input.size.toLong()

    override fun part2(): Long = iterate(input, 100).size.toLong()

    fun parse(sequence: Sequence<String>): List<List<String>> = sequence.map { line ->
        val instruction = mutableListOf<String>()
        var acc = ""
        line.forEach { c ->
            acc += c
            if (this.directions.contains(acc)) {
                instruction.add(acc)
                acc = ""
            }
        }
        instruction
    }.toList()

    fun flip(instructions: List<List<String>>): Set<Tile> {
        val flipped = mutableSetOf<Tile>()
        instructions.forEach { instruction ->
            val destination = instruction.fold(Tile(0, 0, 0)) { tile, d -> tile.next(d) }
            if (!flipped.contains(destination)) flipped.add(destination) else flipped.remove(destination)
        }
        return flipped
    }

    fun iterate(start: Set<Tile>, amount: Int): Set<Tile> {
        val current = start.toMutableSet()
        val flipped = mutableSetOf<Tile>()
        val boundary = hashMapOf<Tile, Int>()
        val neighbors = hashMapOf<Tile, Int>()

        repeat(amount) {
            current.map { it to it.neighbors() }.forEach { (tile, ns) ->
                ns.filter { !current.contains(it) }.forEach { emptyTile ->
                    boundary[emptyTile] = boundary.getOrDefault(emptyTile, 0) + 1
                }
                neighbors[tile] = ns.filter { current.contains(it) }.size
            }
            current.filter { tile ->  neighbors[tile]!!.let { it != 0 && it <= 2 } }.also { flipped.addAll(it) }
            boundary.forEach { (tile, neighbors) -> if (neighbors == 2) flipped.add(tile) }

            current.clear()
            current.addAll(flipped)

            flipped.clear()
            boundary.clear()
            neighbors.clear()
        }
        return current
    }

    data class Tile(val x: Int, val y: Int, val z: Int) {
        fun next(direction: String): Tile = when (direction) {
            "e" -> copy(x = x + 1, y = y - 1)
            "se" -> copy(y = y - 1, z = z + 1)
            "sw" -> copy(x = x - 1, z = z + 1)
            "w" -> copy(x = x - 1, y = y + 1)
            "nw" -> copy(y = y + 1, z = z - 1)
            "ne" -> copy(x = x + 1, z = z - 1)
            else -> this
        }

        fun neighbors(): Set<Tile> = directions.map { next(it) }.toSet()
    }
}