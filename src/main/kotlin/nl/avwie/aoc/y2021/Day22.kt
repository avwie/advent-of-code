package nl.avwie.aoc.y2021

import nl.avwie.aoc.common.*

object Day22 : Day<Int, Long> {

    private val REGEX = Regex("(\\w*) \\w=(-?\\d*)..(-?\\d*),\\w=(-?\\d*)..(-?\\d*),\\w=(-?\\d*)..(-?\\d*)")

    override fun part1(): Int = Input.inputLines(2021, 22).map(::parse)
        .filterNot { (_, c) -> c.v0.x > 50 || c.v0.y > 50 || c.v0.z > 50 || c.v1.x < -50 || c.v1.y < -50 || c.v1.z < -50  }
        .fold(Reactor(setOf())) { acc, (b, c) ->
            if (b) acc.on(c) else acc.off(c)
        }.cubes.size

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    fun parse(line: String): Pair<Boolean, Cuboid> = REGEX.matchEntire(line)!!.groupValues.drop(1)
        .let { r ->
            (r[0] == "on") to Cuboid(
                Vector3D(r[1].toInt(), r[3].toInt(), r[5].toInt()),
                Vector3D(r[2].toInt(), r[4].toInt(), r[6].toInt())
            )
        }

    data class Cuboid(val v0: Vector3D<Int>, val v1: Vector3D<Int>) {
        val xs = v0.x .. v1.x
        val ys = v0.y .. v1.y
        val zs = v0.z .. v1.z

        operator fun contains(v: Vector3D<Int>): Boolean = v.x in xs && v.y in ys && v.z in zs
        fun generate(): Set<Vector3D<Int>> = xs.flatMap { x -> ys.flatMap { y -> zs.map { z -> Vector3D(x, y, z) } } }.toSet()
    }

    data class Reactor(val cubes: Set<Vector3D<Int>>) {
        fun on(cuboid: Cuboid): Reactor = copy(cubes = cubes + cuboid.generate())
        fun off(cuboid: Cuboid): Reactor = copy(cubes = cubes.filter { !cuboid.contains(it) }.toSet())
    }
}