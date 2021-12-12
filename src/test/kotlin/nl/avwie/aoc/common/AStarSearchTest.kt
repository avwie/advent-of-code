package nl.avwie.aoc.common

import nl.avwie.aoc.common.search.AStarSearch
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

class AStarSearchTest {

    data class Node(val pos: Vector2D<Int>, val previous: Node?) {
        val direction = previous?.let { prev -> Direction.get(prev.pos, pos) }
        val steps: Int = (previous?.steps ?: -1) + 1
        val turns: Int = (previous?.turns ?: 0) + if (previous?.direction != null && previous.direction != direction) 1 else 0

        override fun hashCode(): Int {
            return pos.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Node) return false
            return pos == other.pos
        }

        fun retrace(): List<Node> {
            fun inner(acc: List<Node>, node: Node?): List<Node> = when (node) {
                null -> acc
                else -> inner(acc + node, node.previous)
            }
            return inner(listOf(), this)
        }
    }

    @Test
    fun simple() {
        val target = Vector2D(5, 5)
        val search = AStarSearch<Node>(
            found = { node -> node.pos == target },
            children = { node -> node.pos.neighbors(false).map { Node(it, node) } },
            cost = { node -> node.steps.toDouble() },
            heuristic = { node -> (target - node.pos).let { it.x.absoluteValue + it.y.absoluteValue }.toDouble() }
        )
        val result = search.search(Node(Vector2D(0, 0),null)).first().retrace().first()
        assertEquals(target, result.pos)
    }

    @Test
    fun breakingTies() {
        val target = Vector2D(5, 5)
        val search = AStarSearch<Node, Pair<Int, Int>>(
            found = { node -> node.pos == target },
            children = { node -> node.pos.neighbors(false).map { Node(it, node) } },
            cost = { node -> node.steps to node.turns },
            heuristic = { node ->
                val hs = (target - node.pos).let { it.x.absoluteValue + it.y.absoluteValue }
                val ht = node.turns + if (Direction.get(node.pos, target) != node.direction) 1 else 0
                hs to ht
            },
            compare = { (sa, ta), (sb, tb) ->  if (sa == sb) ta.compareTo(tb) else sa.compareTo(sb) },
            sum = { (sa, ta), (sb, tb) -> (sa + sb) to (ta + tb) }
        )

        val result = search.search(Node(Vector2D(0, 0),null)).first().retrace().first()
        assertEquals(1, result.turns)
        assertEquals(target, result.pos)
    }
}