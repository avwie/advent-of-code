package nl.avwie.aoc.common

import java.io.InputStream
import java.net.URL

object Input {

    private fun resource(fileName: String): URL = javaClass.getResource("/$fileName")!!
    private fun resourceAsStream(fileName: String): InputStream = javaClass.getResourceAsStream("/$fileName")!!

    fun read(fileName: String): String = resource(fileName).readText()
    fun readLines(fileName: String): Sequence<String> = resourceAsStream(fileName).bufferedReader().lineSequence()
    fun readInputRegex(lines: Sequence<String>, regex: Regex): Sequence<List<String>> = lines
        .mapIndexed { i, line ->
            require(regex.matches(line)) {"$regex doesn't match line $i: $line"}
            regex.matchEntire(line)!!.groupValues
        }

    fun input(year: Int, day: Int): String = read("$year/day$day.txt")
    fun inputLines(year: Int, day: Int): Sequence<String> = readLines("$year/day$day.txt")
    fun inputLinesRegex(year: Int, day: Int, regex: Regex) = readInputRegex(inputLines(year, day), regex)
}