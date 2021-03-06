package nl.avwie.aoc.common

import java.io.InputStream
import java.net.URL

object Input {

    private fun resource(fileName: String): URL = javaClass.getResource("/$fileName")!!
    private fun resourceAsStream(fileName: String): InputStream = javaClass.getResourceAsStream("/$fileName")!!

    fun read(fileName: String): String = resource(fileName).readText()
    fun readLines(fileName: String): Sequence<String> = resourceAsStream(fileName).bufferedReader().lineSequence()

    fun readRegex(input: String, regex: Regex): List<String> = regex.matchEntire(input)!!.groupValues
    fun readLinesRegex(lines: Sequence<String>, regex: Regex): Sequence<List<String>> = lines
        .mapIndexed { i, line ->
            require(regex.matches(line)) {"$regex doesn't match line $i: $line"}
            regex.matchEntire(line)!!.groupValues
        }

    fun inputStream(year: Int, day: Int): InputStream = resourceAsStream("$year/day$day.txt")
    fun input(year: Int, day: Int): String = read("$year/day$day.txt")
    fun inputRegex(year: Int, day: Int, regex: Regex): List<String> = readRegex(input(year, day), regex)
    fun inputLines(year: Int, day: Int): Sequence<String> = readLines("$year/day$day.txt")
    fun inputLinesRegex(year: Int, day: Int, regex: Regex) = readLinesRegex(inputLines(year, day), regex)
    fun inputLinesRegex(year: Int, day: Int, regex: String) = readLinesRegex(inputLines(year, day), Regex(regex))
}