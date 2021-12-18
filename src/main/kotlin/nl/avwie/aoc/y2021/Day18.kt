package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import java.util.*

object Day18 : Day<Int, Int> {
    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun parse(line: String): Tree {
        var lastDigit: Tree.Leaf? = null
        val stack = Stack<Tree>()
        line.forEach { c ->
            when {
                c.isDigit() -> {
                    val leaf = Tree.Leaf(c.digitToInt())
                    leaf.left = lastDigit
                    lastDigit?.right = leaf
                    lastDigit = leaf
                    stack.push(leaf)
                }
                c == ']' -> (stack.pop() to stack.pop()).also { (r, l) ->
                    val node = Tree.Branch(l, r)
                    l.parent = node
                    r.parent = node
                    stack.push(node)
                }
            }
        }
        return stack.pop()
    }

    sealed interface Tree {

        val depth: Int
        var parent: Branch?

        fun explode(): Boolean
        fun split(): Boolean
        fun flatten(): List<Tree>

        class Branch(var left: Tree?, var right: Tree?, override var parent: Branch? = null): Tree {
            private val explodable: Boolean get() = depth >= 4 && left is Leaf && right is Leaf
            override val depth: Int get() = (parent?.depth ?: -1) + 1
            override fun toString() = "[${left.toString()},${right.toString()}]"

            override fun explode(): Boolean {
                if (!explodable) return listOfNotNull(left, right).any { it.explode() }

                val leftLeaf = left as Leaf
                val rightLeaf = right as Leaf
                val newValue = Leaf(0, parent = parent)
                leftLeaf.left?.also {
                    it.value += leftLeaf.value
                    it.right = newValue
                    newValue.left = it
                }
                rightLeaf.right?.also {
                    it.value += rightLeaf.value
                    it.left = newValue
                    newValue.right = it
                }

                if (parent!!.left == this) parent!!.left = newValue
                else parent!!.right = newValue
                return true
            }

            override fun split(): Boolean = listOfNotNull(left, right).any { it.split() }

            override fun flatten(): List<Tree> = listOf(this) + listOfNotNull(left, right).flatMap { it.flatten() }
        }

        class Leaf(var value: Int, override var parent: Branch? = null, var left: Leaf? = null, var right: Leaf? = null): Tree {
            override val depth: Int get() = (parent?.depth ?: 0)
            override fun toString() = value.toString()
            override fun flatten(): List<Tree> = listOf(this)
            override fun explode(): Boolean = false

            override fun split(): Boolean {
                if (value < 10) return false
                val leftValue = Leaf(value / 2)
                val rightValue = Leaf(value - leftValue.value)
                val branch = Branch(leftValue, rightValue, parent)
                leftValue.parent = branch
                rightValue.parent = branch

                leftValue.right = rightValue
                rightValue.left = leftValue
                left?.also {
                    leftValue.left = it
                    it.right = leftValue
                }
                right?.also {
                    rightValue.right = it
                    it.left = rightValue
                }
                if (parent!!.left == this) parent!!.left = branch
                else parent!!.right = branch
                return true
            }
        }
    }
}