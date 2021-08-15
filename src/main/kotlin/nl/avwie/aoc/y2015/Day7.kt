package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day7 : Day<Int, Int> {

    override fun part1(): Int = eval("a", gates(Input.inputLines(2015, 7)))

    override fun part2(): Int = eval("a", gates(Input.inputLines(2015, 7)), mutableMapOf("b" to part1()))

    fun gates(input: Sequence<String>): Map<String, String> = input.map { line ->
        line.split(" -> ").let { (gate, wire) -> wire to gate }
    }.toMap()

    fun eval(wire: String, gates: Map<String, String>, values: MutableMap<String, Int> = mutableMapOf()): Int = values.getOrPut(wire) { wire.toIntOrNull()
        val wiring = gates[wire]
        val calc : (String) -> Int = { eval(it, gates, values) }

        when {
            wiring == null -> wire.toInt()
            wiring.startsWith("NOT") -> calc(wiring.split(" ")[1]).inv() and 0xffff
            wiring.contains("AND") -> wiring.split(" ").let { (x, _, y) -> calc(x) and calc(y) }
            wiring.contains("OR") -> wiring.split(" ").let { (x, _, y) -> calc(x) or calc(y) }
            wiring.contains("LSHIFT") -> wiring.split(" ").let { (x, _, y) -> calc(x) shl calc(y) }
            wiring.contains("RSHIFT") -> wiring.split(" ").let { (x, _, y) -> calc(x) shr calc(y) }
            else -> calc(wiring)
        }
    }
}