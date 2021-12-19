package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*
import kotlin.math.abs

object Day19 : Day<Int, Int> {

    private val scanners = parse(Input.inputLines(2021, 19))
    private val offsets = offsets(scanners)

    override fun part1(): Int = merge(offsets).report.size

    override fun part2(): Int = combinations(offsets.values, 2).maxOf { (a, b) ->
        abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
    }

    private fun merge(offsets: Map<Scanner, Vector3D<Int>>): Scanner {
        val init = offsets.entries.first { (_, o) -> o == Vector3D(0, 0, 0) }.key
        return offsets.entries.filter { it.key != init }.fold(init) { acc, (scanner, offset) ->
            acc.merge(scanner, offset)
        }
    }

    private fun offsets(scanners: List<Scanner>): Map<Scanner, Vector3D<Int>> {
        val result = mutableListOf<Pair<Scanner, Vector3D<Int>>>()
        var init = scanners.first()
        result.add(init to Vector3D(0, 0, 0))

        val remaining = scanners.drop(1).toMutableSet()
        val toRemove = mutableSetOf<Scanner>()
        while (remaining.isNotEmpty()) {
            remaining.forEach { other ->
                val offsetResult = init.offsetTo(other)
                if (offsetResult != null) {
                    result.add(offsetResult)
                    toRemove.add(other)
                    init = init.merge(offsetResult.first, offsetResult.second)
                    return@forEach
                }
            }
            remaining.removeAll(toRemove)
        }
        return result.toMap()
    }

    private fun parse(lines: Sequence<String>): List<Scanner> {
        var acc = mutableListOf<Vector3D<Int>>()
        val scanners = mutableListOf<Scanner>()
        lines.forEach { line ->
            if (line.startsWith("---")) {
                if (acc.isNotEmpty()) scanners.add(Scanner(acc))
                acc = mutableListOf()
            } else if (line.isNotEmpty()) {
                val (x, y, z) = line.split(",").map { it.toInt() }
                acc.add(Vector3D(x, y, z))
            }
        }
        scanners.add(Scanner(acc))
        return scanners
    }

    data class Scanner(val report: List<Vector3D<Int>>) {
        val rotations by lazy {
            Quaternion.rotations.map { q ->
                report.map { p -> p.toDouble().rotate(q).roundToInt(1E-6) }
            }
        }

        fun offsetTo(other: Scanner): Pair<Scanner, Vector3D<Int>>? {
            other.rotations.forEach { otherReport ->
                val offsets = mutableMapOf<Vector3D<Int>, Int>()
                report.forEach { pos ->
                    otherReport.forEach { otherPos ->
                        val offset = otherPos - pos
                        offsets[offset] = (offsets[offset] ?: 0) + 1

                        if ((offsets[offset] ?: 0 )>= 12) {
                            return Scanner(otherReport) to -offset
                        }
                    }
                }
            }
            return null
        }

        fun merge(other: Scanner, offset: Vector3D<Int>): Scanner {
            val mergedReport = (report + other.report.map { it + offset }).distinct()
            return Scanner(mergedReport)
        }
    }
}