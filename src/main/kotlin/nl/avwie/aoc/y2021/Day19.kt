package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day19 : Day<Int, Int> {
    override fun part1(): Int {
        TODO("Not yet implemented")
    }

    override fun part2(): Int {
        TODO("Not yet implemented")
    }

    fun parse(lines: Sequence<String>): List<Scanner> {
        var acc = mutableListOf<Vec3D>()
        val scanners = mutableListOf<Scanner>()
        lines.forEach { line ->
            if (line.startsWith("---")) {
                if (acc.isNotEmpty()) scanners.add(Scanner(acc))
                acc = mutableListOf()
            } else if (line.isNotEmpty()) {
                val (x, y, z) = line.split(",").map { it.toInt() }
                acc.add(Vec3D(x, y, z))
            }
        }
        scanners.add(Scanner(acc))
        return scanners
    }

    class Scanner(val report: List<Vec3D>) {
        val allReports = combinations(listOf(-1, 0, 1), 3).map { (dx, dy, dz) ->
            report.map { (x, y, z) -> Vec3D(x * dx, y * dy, z * dz) }
        }

        fun match(other: Scanner): List<Vec3D>? {
            other.allReports.forEach { otherReport ->
                val offsets = mutableMapOf<Vec3D, Int>()
                report.forEach { pos ->
                    otherReport.forEach { otherPos ->
                        val offset = otherPos - pos
                        offsets[otherPos - pos] = (offsets[offset] ?: 0) + 1

                        if ((offsets[offset] ?: 0 )>= 12) {
                            return other.report.map { it + offset }
                        }
                    }
                }
            }
            return null
        }
    }
}