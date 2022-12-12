package nl.avwie.aoc.common

import nl.avwie.aoc.common.Vector2D

fun parseLineSequenceToMap(lines: Sequence<String>) = lines
    .flatMapIndexed { row: Int, line: String ->
        line.mapIndexed { col, char ->
            Vector2D(col, row) to char
        }
    }
    .toMap()