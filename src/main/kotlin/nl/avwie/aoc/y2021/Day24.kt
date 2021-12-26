package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import java.lang.IllegalArgumentException
import java.util.*

object Day24 : Day<Long, Long> {

    override fun part1(): Long = generateSequence(99999999999999) { it - 1}.filter {
        if (it % 100000000 == 0L) println(it)
        evaluate(it) == 0
    }.first()

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    /*data class Instruction(val cmd: String, val arg0: String, val arg1: String) {
        val arg0Loc = loc[arg0]!!
        fun getArg1(memory: Array<Long>) = arg1.toLongOrNull() ?: memory[loc[arg1]!!]

        operator fun invoke(inputs: Stack<Int>, memory: Array<Long>) = when (cmd) {
            "inp" -> memory[arg0Loc] = inputs.pop().toLong()
            "add" -> memory[arg0Loc] = memory[arg0Loc] + getArg1(memory)
            "mul" -> memory[arg0Loc] = memory[arg0Loc] * getArg1(memory)
            "div" -> memory[arg0Loc] = memory[arg0Loc] / getArg1(memory)
            "mod" -> memory[arg0Loc] = memory[arg0Loc] + getArg1(memory)
            "eql" -> memory[arg0Loc] = if (memory[arg0Loc] == getArg1(memory)) 1L else 0L
            else -> {}
        }

        companion object {
            private val loc = mapOf("w" to 0, "x" to 1, "y" to 2, "z" to 3)
        }
    }*/

    /*fun evaluate(input: Array<Long>) {
        var w = 0
        var x = 0
        var y = 0
        var z = 0
    }*/

    inline fun getMask(long: Long): Long {
        var l = 1L
        while (long % l != long) {
            l *= 10
        }
        return l / 10
    }

    inline fun getInput(long: Long): Pair<Int, Long> {
        val s = getMask(long)
        if (s == 0L) return long.toInt() to 0L

        val r = long / s
        return r.toInt() to long % s
    }

    fun generateCode(lines: List<String>): String {
        var i = 0
        val builder = StringBuilder()
        builder.appendLine("fun evaluate(check: Long): Int {")
        builder.appendLine("\tvar w = 0")
        builder.appendLine("\tvar x = 0")
        builder.appendLine("\tvar y = 0")
        builder.appendLine("\tvar z = 0")
        builder.appendLine("\tvar input = check")

        lines.forEach { line ->
            val parts = line.split(" ")
            when (parts[0]) {
                "inp" -> {
                    builder.appendLine("\trun {")
                    builder.appendLine("\t\tval (i, r) = getInput(input)")
                    builder.appendLine("\t\tinput = r")
                    builder.appendLine("\t\t${parts[1]} = i")
                    builder.appendLine("\t}")
                }
                "add" -> builder.appendLine("\t${parts[1]} = ${parts[1]} + ${parts[2]}")
                "mul" -> builder.appendLine("\t${parts[1]} = ${parts[1]} * ${parts[2]}")
                "div" -> builder.appendLine("\t${parts[1]} = ${parts[1]} / ${parts[2]}")
                "mod" -> builder.appendLine("\t${parts[1]} = ${parts[1]} % ${parts[2]}")
                "eql" -> builder.appendLine("\t${parts[1]} = if (${parts[1]} == ${parts[2]}) 1 else 0")
            }
        }

        builder.appendLine("\treturn z")
        builder.appendLine("}")
        return builder.toString()
    }

    fun evaluate(check: Long): Int {
        var w = 0
        var x = 0
        var y = 0
        var z = 0
        var input = check
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 10
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 10
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 13
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 5
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 15
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 12
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -12
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 12
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 14
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 6
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -2
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 4
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 13
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 15
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -12
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 3
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 15
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 7
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 1
        x = x + 11
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 11
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -3
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 2
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -13
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 12
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -12
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 4
        y = y * x
        z = z + y
        run {
            val (i, r) = getInput(input)
            input = r
            w = i
        }
        x = x * 0
        x = x + z
        x = x % 26
        z = z / 26
        x = x + -13
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        y = y * 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + 11
        y = y * x
        z = z + y
        return z
    }



}