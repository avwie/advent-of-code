package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.search.depthFirstSearch

object Day12 : Day<Int, Int> {

    data class Node(val location: String, val visited: Set<String>, val allowSmallCave: Boolean)

    val input = parse(Input.inputLines(2021, 12))

    override fun part1(): Int = countPaths("start", "end", input, false)
    override fun part2(): Int = countPaths("start", "end", input, true)

    fun parse(lines: Sequence<String>): Map<String, List<String>> = lines.fold(mapOf()) { edges, line ->
        val (a, b) = line.split("-")
        edges + (a to edges.getOrDefault(a, listOf()) + b) + (b to edges.getOrDefault(b, listOf()) + a)
    }

    fun countPaths(start: String, target: String, edges: Map<String, List<String>>, allowSmallCave: Boolean): Int =
        depthFirstSearch(
            init = Node(start, setOf(start), allowSmallCave),
            found = { node -> node.location == target },
            children = { node ->
                edges[node.location]!!
                    .filter { it.first().isUpperCase() || !node.visited.contains(it) || (node.visited.contains(it) && node.allowSmallCave) }
                    .filter { it != "start" }
                    .map { neighbor ->
                        Node(
                            location = neighbor,
                            visited = node.visited + neighbor,
                            allowSmallCave = node.allowSmallCave && (neighbor.first().isUpperCase() || !node.visited.contains(neighbor))
                        )
                    }
            }
        ).count()
}