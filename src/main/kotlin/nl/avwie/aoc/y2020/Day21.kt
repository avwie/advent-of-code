package nl.avwie.aoc.y2020

import java.util.*
import nl.avwie.aoc.common.Day
import nl.avwie.aoc.common.Input

object Day21 : Day<Int, String> {

    private val REGEX = Regex("(.*|\\s*) \\(contains (.*)\\)")
    private val input by lazy {
        Input.inputLines(2020, 21).let(::parse)
    }

    override fun part1(): Int = input.let { (foods, allergens) ->
        allergens.values.toSet().let { allergenIngredients ->
            foods.map { (it - allergenIngredients).size }.sum()
        }
    }

    override fun part2(): String = input.second.let { allergens ->
        allergens.map { it.key to it.value }.sortedBy { it.first }.joinToString(",") { it.second }
    }

    fun parse(input: Sequence<String>): Pair<List<Set<String>>,Map<String,String>> {
        val foods = mutableListOf<Set<String>>()
        val map = mutableMapOf<String, Set<String>>()
        input.forEach { line ->
            REGEX.matchEntire(line)!!.groupValues.also { (_, ingredientsLine, allergensLine) ->
                val ingredients = ingredientsLine.split(" ").toSet().also { foods.add(it) }
                val allergens = allergensLine.split(", ")
                allergens.forEach { allergen ->
                    map[allergen] = map.getOrDefault(allergen, ingredients).intersect(ingredients)
                }
            }
        }
        return foods to isolate(map)
    }

    private fun isolate(map: Map<String, Set<String>>): Map<String, String> {
        val result = mutableMapOf<String, String>()
        val toEliminate = Stack<String>()
        val remaining = mutableMapOf<String, Set<String>>()

        map.entries.partition { (_, ingredients) -> ingredients.size == 1 }.also { (singular, multiple) ->
            singular.forEach { (allergen, ingredients) ->
                result[allergen] = ingredients.first()
                toEliminate.add(ingredients.first())
            }
            multiple.forEach { (allergen, ingredients) -> remaining[allergen] = ingredients }
        }

        while (toEliminate.isNotEmpty()) {
            val ingredient = toEliminate.pop()
            val toRemove = mutableSetOf<String>()
            remaining.forEach { (allergen, ingredients) ->
                remaining[allergen] = (ingredients - ingredient).also { remainingIngredients ->
                    if (remainingIngredients.size == 1) {
                        result[allergen] = remainingIngredients.first()
                        toEliminate.add(remainingIngredients.first())
                        toRemove.add(allergen)
                    }
                }
            }
            toRemove.forEach { remaining.remove(it) }
        }
        return result
    }
}