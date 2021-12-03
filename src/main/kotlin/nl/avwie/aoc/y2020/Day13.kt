package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import nl.avwie.aoc.common.linearSeq

object Day13 : Day<Long, Long> {

    val input = Input.inputLines(2020,13).toList().let(::parse)

    fun parse(input: List<String>): Pair<Long, List<Pair<Int, Long>>> = input.let { (timestamp, ids) ->
        timestamp.toLong() to ids.split(",").withIndex().filter { it.value != "x" }.map { it.index to it.value.toLong() }
    }

    override fun part1(): Long = input.let { (timestamp, ids) -> earliest(timestamp, ids) }
    override fun part2(): Long = input.let { (_, ids) -> search(ids) }

    fun earliest(timestamp: Long, ids: List<Pair<Int, Long>>) = ids
        .map { (_, id) -> id to  (id - timestamp % id) }
        .minByOrNull { (_, wait) -> wait }!!.let { (id, wait) -> id * wait }

    fun search(ids: List<Pair<Int, Long>>) = ids.fold(linearSeq(start = 0L, step = 1L)) { seq, (offset, id) ->
        seq.filter { (it + offset) % id == 0L }
            .take(2).toList()
            .let { (a, b) -> linearSeq(start = a, step = b - a) }
    }.first()
}
