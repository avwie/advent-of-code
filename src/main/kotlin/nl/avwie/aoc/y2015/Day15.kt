package nl.avwie.aoc.y2015

import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input
import kotlin.math.max

object Day15 : Day<Long, Long> {
    data class Ingredient(val name: String, val capacity: Long, val durability: Long, val flavor: Long, val texture: Long, val calories: Long)

    private val REGEX = "(\\w*): capacity (-?\\d*), durability (-?\\d*), flavor (-?\\d*), texture (-?\\d*), calories (-?\\d*)".toRegex()

    override fun part1(): Long = parse(Input.inputLines(2015, 15)).toList().let { ingredients ->
        amounts().maxOf { amounts ->
            score(amounts, ingredients)
        }
    }

    override fun part2(): Long = parse(Input.inputLines(2015, 15)).toList().let { ingredients ->
        amounts()
            .filter { amounts -> calories(amounts, ingredients) == 500L }
            .maxOf { amounts ->
                score(amounts, ingredients)
            }
    }

    fun parse(lines: Sequence<String>): Sequence<Ingredient> = lines.map { line ->
        REGEX.matchEntire(line)!!.destructured.let { (name, capacity, durability, flavor, texture, calories) ->
            Ingredient(name, capacity.toLong(), durability.toLong(), flavor.toLong(), texture.toLong(), calories.toLong())
        }
    }

    fun amounts(): Sequence<List<Int>> = sequence {
        (0 .. 100).forEach { a ->
            (0 .. (100 - a)).forEach { b ->
                (0 .. (100 - (a + b))).forEach { c ->
                    val d = 100 - (a + b + c)
                    yield(listOf(a, b, c, d))
                }
            }
        }
    }

    fun score(amounts: List<Int>, ingredients: List<Ingredient>): Long {
        val zipped = ingredients.zip(amounts)
        val capacity = max(0, zipped.sumOf { (ingredient, count) -> ingredient.capacity * count })
        val durability = max(0, zipped.sumOf { (ingredient, count) -> ingredient.durability * count })
        val flavor = max(0, zipped.sumOf { (ingredient, count) -> ingredient.flavor * count })
        val texture = max(0, zipped.sumOf { (ingredient, count) -> ingredient.texture * count })
        return capacity * durability * flavor * texture
    }

    fun calories(amounts: List<Int>, ingredients: List<Ingredient>): Long {
        return ingredients.zip(amounts).sumOf { (ingredient, count) -> ingredient.calories * count }
    }
}