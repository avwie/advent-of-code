package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day12 : Day<Int, Int> {

    data class Node(val location: String, val target: String,
                    val visited: Set<String>, val edges: Map<String, List<String>>,
                    val allowSmallCave: Boolean) {

        val isFinished: Boolean = location == target

        private val validNeighbors = edges[location]!!
            .filter { it.first().isUpperCase() || !visited.contains(it) || (visited.contains(it) && allowSmallCave) }
            .filter { it != "start" }

        fun next(): List<Node> =  validNeighbors.map { neighbor ->
            Node(
                neighbor,
                target,
                visited + neighbor,
                edges,
                allowSmallCave && (neighbor.first().isUpperCase() || !visited.contains(neighbor))
            )
        }
    }

    val input = parse(Input.inputLines(2021, 12))

    override fun part1(): Int = countPaths("start", "end", input, false)
    override fun part2(): Int = countPaths("start", "end", input, true)

    fun parse(lines: Sequence<String>): Map<String, List<String>> = lines.fold(mapOf()) { edges, line ->
        val (a, b) = line.split("-")
        edges + (a to edges.getOrDefault(a, listOf()) + b) + (b to edges.getOrDefault(b, listOf()) + a)
    }

    fun countPaths(start: String, target: String, edges: Map<String, List<String>>, allowSmallCave: Boolean): Int {
        val nodes = ArrayDeque<Node>()
        var result = 0

        nodes.add(Node(start, target, setOf(start), edges, allowSmallCave))
        while (nodes.isNotEmpty()) {
            val current = nodes.removeFirst()
            val neighbors = current.next()
            neighbors.forEach { neighbor ->
                if (neighbor.isFinished) result += 1
                else nodes.addLast(neighbor)
            }
        }
        return result
    }
}