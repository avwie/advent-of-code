package nl.avwie.aoc.common

enum class Direction {
    North,
    East,
    South,
    West;

    fun opposite(): Direction = when (this) {
        North -> South
        East -> West
        South -> North
        West -> East
    }
}