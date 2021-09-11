package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import java.lang.Integer.max

object Day21 : Day<Int, Int> {

    data class Stat(val cost: Int, val damage: Int, val armor: Int) {
        operator fun plus(other: Stat): Stat = Stat(cost + other.cost, damage + other.damage, armor + other.armor)
    }

    private val empty = Stat(0, 0, 0)
    private val weapons = listOf(Stat(8, 4, 0), Stat(10, 5, 0), Stat(25, 6, 0), Stat(40, 7, 0), Stat(74, 8, 0))
    private val armors = listOf(Stat(13, 0, 1), Stat(31, 0, 2), Stat(53, 0, 3), Stat(75, 0, 4), Stat(102, 0, 5))
    private val rings = listOf(Stat(25, 1, 0), Stat(50, 2, 0), Stat(100, 3, 0), Stat(20, 0, 1), Stat(40, 0, 2), Stat(80, 0, 3))

    private val boss = 100 to Stat(0, 8, 2)

    override fun part1(): Int = gears().sortedBy { it.cost }.first { stat ->
        battle(100 to stat, boss).last().first > 0
    }.cost

    override fun part2(): Int = gears().sortedBy { -it.cost }.first { stat ->
        battle(100 to stat, boss).last().second > 0
    }.cost

    fun gears(): Sequence<Stat> = sequence {
        weapons.forEach { weapon ->
            (listOf(empty) + armors).forEach { armor ->
                (-2 until rings.size).forEach { r1 ->
                    (r1 + 1 until rings.size).forEach { r2 ->
                        val ring1 = rings.getOrNull(r1) ?: empty
                        val ring2 = rings.getOrNull(r2) ?: empty
                        yield(weapon + armor + ring1 + ring2)
                    }
                }
            }
        }
    }

    fun battle(left: Pair<Int, Stat>, right: Pair<Int, Stat>): Sequence<Pair<Int, Int>> = sequence {
        var turn = true
        var (hl, hr) = left.first to right.first
        while (hl > 0 && hr > 0) {
            yield(hl to hr)
            if (turn) {
                hr -= max(1,left.second.damage - right.second.armor)
            } else {
                hl -= max(1,right.second.damage - left.second.armor)
            }
            turn = !turn
        }
        yield(hl to hr)
    }
}