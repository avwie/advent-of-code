package nl.avwie.aoc.y2020

import nl.avwie.aoc.common.*
import nl.avwie.aoc.common.Complex.Companion.j
import java.util.*
import kotlin.math.sqrt
import kotlin.random.Random
import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day20 : Day<Long, Long> {

    val MONSTER = """
        ..................#.
        #....##....##....###
        .#..#..#..#..#..#...
    """.trimIndent().lines().let { lines -> Image.fromString(Random.nextLong(), lines) }

    private val reconstructed : TileMap by lazy {
        Input.inputLines(2020, 20).let(::parse).let(::reconstruct)
    }

    override fun part1(): Long = reconstructed.corners().map { it.id }.reduce { l, r -> l * r }

    override fun part2(): Long = reconstructed.stitch(1).let { image ->
        image.pixels.size - MONSTER.pixels.size * findMonster(image)
    }.toLong()

    fun parse(input: Sequence<String>): List<Image> {
        val tiles = mutableListOf<Image>()
        var currentId = -1L
        val acc = mutableListOf<String>()
        input.forEach { line ->
            when {
                line.startsWith("Tile ") -> currentId = line.split(" ")[1].dropLast(1).toLong()
                line.isBlank() -> {
                    tiles.add(Image.fromString(currentId, acc))
                    acc.clear()
                }
                else -> acc.add(line)
            }
        }
        tiles.add(Image.fromString(currentId, acc))
        return tiles
    }

    fun reconstruct(tiles: List<Image>): TileMap {
        val maps = Stack<TileMap>()
        val dim = sqrt(tiles.size.toDouble()).toInt()

        maps.add(TileMap(dim, dim, listOf(), tiles.toSet()))

        var answer: TileMap? = null
        while (answer == null) {
            val map = maps.pop()
            if (map.isComplete) {
                answer = map
            } else {
                maps.addAll(map.next())
            }
        }
        return answer
    }

    fun findMonster(image: Image): Int {
        return image.variations().asSequence().map { source ->
            (0 until source.height).flatMap { r ->  (0 until source.width).map { c -> r to c } }
                .count { (r, c) ->
                    source.pixels.containsAll(MONSTER.pixels.map { p -> p + r + c * j })
                }
        }.first {  it > 0 }
    }

    data class Image(val id: Long, val pixels: Set<Complex>, val width: Int, val height: Int) {

        fun rotateLeft(): Image = Image(id, pixels.map { it * j + (height - 1) }.toSet(), height, width)
        fun rotateRight(): Image = Image(id, pixels.map { it * -j + (width - 1) * j }.toSet(), height, width)
        fun flipHorizontal(): Image = Image(id, pixels.map { it.copy(i = -it.i + (width - 1)) }.toSet(), width, height)
        fun flipVertical(): Image = Image(id, pixels.map { it.copy(j = -it.j + (height - 1)) }.toSet(), width, height)

        fun variations(): Set<Image> = listOf(this)
            .reduceRepeated(3) { tiles -> tiles + tiles.last().rotateRight() }
            .flatMap { tile -> listOf(tile, tile.flipHorizontal(), flipVertical()) }
            .toSet()

        fun edge(direction: Direction): Set<Long> = when (direction) {
            Direction.North -> pixels.filter { it.j == (height - 1).toLong() }.map { it.i }.toSet()
            Direction.East -> pixels.filter { it.i == (width - 1).toLong() }.map { it.j }.toSet()
            Direction.South -> pixels.filter { it.j == 0L }.map { it.i }.toSet()
            Direction.West -> pixels.filter { it.i == 0L }.map { it.j }.toSet()
            else -> setOf()
        }

        fun crop(amount: Int) = copy(
            width = width - 2 * amount,
            height = height - 2 * amount,
            pixels = pixels
                .filter { c -> c.j >= amount && c.j < (height - amount) && c.i >= amount && c.i < (width - amount) }
                .map { c -> c - (amount + amount * j)}
                .toSet()
        )

        override fun toString(): String {
            return (0 until height).map { r ->
                (0 until width).map { c -> if (pixels.contains(c + (height - r - 1) * j)) '#' else '.' }.joinToString("")
            }.joinToString("\n")
        }

        companion object {
            fun fromString(id: Long, data: List<String>): Image {
                val pixels = mutableSetOf<Complex>()
                data.forEachIndexed { r, line ->
                    line.forEachIndexed { c, char ->
                        if (char == '#') {
                            pixels.add(c + (data.size - r - 1) * j)
                        }
                    }
                }
                return Image(id, pixels, data[0].length, data.size)
            }
        }

    }

    data class TileMap(val width: Int, val height: Int, val tiles: List<Image>, val options: Set<Image>) {
        val isComplete = tiles.size == width * height
        val isValid = (!isComplete && options.isNotEmpty()) || isComplete

        fun corners(): List<Image> = listOf(0, width -1, width * (height - 1), width * height - 1)
            .mapNotNull { tiles.getOrNull(it) }

        fun next(): List<TileMap> {
            val idx = tiles.size
            val (row, col) = idx / height to idx % height
            val edges = mutableSetOf<Pair<Direction, Set<Long>>>()

            if (row > 0) edges.add(Direction.North to tiles[idx - width].edge(Direction.South))
            if (col > 0) edges.add(Direction.West to tiles[idx - 1].edge(Direction.East))

            val matches = when (idx) {
                0 -> options.flatMap { it.variations() }
                else -> options.mapNotNull { tile ->
                    tile.variations().firstOrNull{ variation -> edges.all { (direction, edge) -> variation.edge(direction) == edge } }
                }
            }

            return matches
                .map { tile -> copy(tiles = tiles + tile, options = options - options.filter { it.id == tile.id }.toSet()) }
                .filter { image -> image.isValid }
        }

        fun stitch(cropAmount: Int): Image {
            val cropped = tiles.map { it.crop(cropAmount) }
            val (tileWidth, tileHeight) = cropped.first().width to cropped.first().height
            val (imageWidth, imageHeight) = width * tileWidth to height * tileHeight
            val pixels = mutableSetOf<Complex>()

            cropped.forEachIndexed { idx, tile ->
                val (row, col) = (height - 1) - idx / height to idx % height
                pixels.addAll(tile.pixels.map { pixel -> pixel + (col * tileWidth + row * tileHeight * j) })
            }
            return Image(Random.nextLong(), pixels, imageWidth, imageHeight)
        }
    }
}