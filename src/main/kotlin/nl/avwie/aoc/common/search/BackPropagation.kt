package nl.avwie.aoc.common.search

import java.util.*

class BackPropagation<T>(
    val implementation: Implementation<T>
)
{
    interface Implementation<T> {
        fun isFound(item: T): Boolean
        fun next(item: T): T?
    }

    fun search(init: T): T? {
        val stack = Stack<T>()
        stack.add(init)

        var i = 0
        while (stack.isNotEmpty() && !implementation.isFound(stack.peek())) {
            val next = implementation.next(stack.peek())
            if (next == null) stack.pop()
            else stack.add(next)

            i += 1
            if (i % 10000 == 0) {
                println("Iteration: $i")
            }
        }

        return stack.pop() ?: null
    }
}