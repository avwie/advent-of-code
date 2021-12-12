package nl.avwie.aoc.common

import nl.avwie.aoc.common.search.AStarSearch
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

class AStarSearchTest {

    data class Node(val pos: Vector2D<Int>, val steps: Int, val previous: Node?) {
        override fun hashCode(): Int {
            return pos.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Node) return false
            return pos == other.pos
        }

        fun retrace(): List<Vector2D<Int>> {
            fun inner(acc: List<Vector2D<Int>>, node: Node?): List<Vector2D<Int>> = when (node) {
                null -> acc
                else -> inner(acc + node.pos, node.previous)
            }
            return inner(listOf(), this)
        }
    }

    @Test
    fun simple() {
        val target = Vector2D(5, 5)
        val search = AStarSearch<Node>(
            found = { node -> node.pos == target },
            children = { node -> node.pos.neighbors(false).map { Node(it, node.steps + 1, node) } },
            cost = { node -> node.steps.toDouble() },
            heuristic = { node -> (target - node.pos).let { it.x.absoluteValue + it.y.absoluteValue }.toDouble() }
        )

        val result = search.search(Node(Vector2D(0, 0), 0, null)).first().retrace()
        assertEquals(target, result.first())
    }
}