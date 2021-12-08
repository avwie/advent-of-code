package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day8 : Day<Int, Long> {

    override fun part1(): Int = Input.inputLines(2021, 8)
        .map { line -> line.split(" | ")[1] }
        .sumOf { output ->
            output.split(" ").count { setOf(2, 4, 3, 7).contains(it.length) }
        }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}
 /**
 0 = [0, 1, 2, ., 4, 5, 6]
 1 = [., ., 2, ., ., 5, .]
 2 = [0, ., 2, 3, 4, ., 6]
 3 = [0, ., 2, 3, ., 5, 6]
 4 = [., 1, 2, 3, ., 5, .]
 5 = [0, 1, ., 3, ., 5, 6]
 6 = [0, 1, ., 3, 4, 5, 6]
 7 = [0, ., 2, ., ., 5, .]
 8 = [0, 1, 2, 3, 4, 5, 6]
 9 = [0, 1, 2, 3, ., 5, 6]
 8, 6, 8, 7, 4, 9, 7
 0, 1, 2, 3, 4, 5, 6

 acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab
 a = 8
 b = 9
 c = 7
 d = 8
 e = 6
 f = 7
 g = 4

 d1 = ab
 d4 = abef
 d7 = abd
 d8 = abcdefg

 s1 = 6x
 s4 = 4x
 s5 = 9x

 Options:
 s0 = [d]
 s1 = [e]
 s2 = [a]
 s3 = [f]
 s4 = [g]
 s5 = [b]
 s6 = [c]

  **/