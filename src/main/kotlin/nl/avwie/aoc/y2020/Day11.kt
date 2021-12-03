package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.offsets
import nl.avwie.aoc.common.rays

object Day11 : Day<Long, Long> {

    val OCCUPIED = 35
    val FLOOR = 46
    val FREE = 76

    val input=  Input.inputLines(2020, 11).let(::parse)

    override fun part1(): Long = answer(updates(input, 1, 4))

    override fun part2(): Long = answer(updates(input, 2, 5))

    fun parse(input: Sequence<String>): Seating = input.toList().let { Seating(it.size, it.first().length, it.joinToString("").chars().toArray()) }

    fun answer(seatings: Sequence<Seating>) = seatings
        .map { it to it.seatsOccupied }
        .windowed(2)
        .takeWhile { (prev, next) -> prev.second != next.second }
        .last().last().second.toLong()

    fun updates(seating: Seating, part: Int, limit: Int) = generateSequence(seating) { it.update(part, limit ) }

    class Seating(val rows: Int, val cols: Int, private val data: IntArray, seatList: List<Pair<Int, Int>>? = null, occupied: Int? = null) {

        val seats: List<Pair<Int, Int>> = seatList ?: (0 until rows).flatMap { r -> (0 until cols).map { c -> r to c } }.filter { (r, c) -> this[r, c] != FLOOR }
        var seatsOccupied: Int = occupied ?: seats.count { (r, c) -> isOccupied(r, c) }
            private set

        fun copy(): Seating = Seating(rows, cols, data.clone(), seats, seatsOccupied)

        operator fun get(r: Int, c: Int): Int = data[r * cols + c]
        operator fun set(r: Int, c: Int, v: Int) {
            if (get(r, c) == FREE && v == OCCUPIED) seatsOccupied++
            else if (get(r, c) == OCCUPIED && v == FREE) seatsOccupied --
            data[r * cols + c] = v
        }

        fun contains(r: Int, c: Int): Boolean = when {
            r < 0 || r >= rows -> false
            c < 0 || c >= cols -> false
            else -> true
        }


        fun occupiedByOffsets(r: Int, c: Int): List<Boolean> = offsets.map { (i, j) -> isOccupied(r + i, c + j) }

        fun occupiedByRays(r: Int, c: Int) = rays.map { ray ->
            ray.map { (i, j) -> (r + i) to (c + j) }
                .first { (i, j) -> !contains(i, j) || this[i, j] != FLOOR }
                .let { (i, j) -> isOccupied(i, j) }
        }

        fun isOccupied(r: Int, c: Int) = when {
            !contains(r, c) -> false
            else -> this[r, c] == OCCUPIED
        }

        fun update(mode: Int, limit: Int): Seating = copy().also { copy ->
            seats.forEach { (r, c) ->
                val neighbours = when (mode) {
                    1 -> occupiedByOffsets(r, c)
                    2 -> occupiedByRays(r, c)
                    else -> emptyList()
                }
                when {
                    this[r, c] == FREE && neighbours.all { !it } -> copy[r, c] = OCCUPIED
                    this[r, c] == OCCUPIED && neighbours.filter { it }.size >= limit -> copy[r, c] = FREE
                }
            }
        }
    }
}