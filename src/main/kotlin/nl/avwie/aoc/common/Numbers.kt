package nl.avwie.aoc.common

data class Base(val values: List<Int>, val base: Int) {

    operator fun plus(other: Base): Base {
        require(base == other.base)
        val buffer = mutableListOf<Int>()

        var carry = 0
        var index = 0
        do {
            carry += values.getOrNull(index) ?: 0
            carry += other.values.getOrNull(index) ?: 0
            buffer.add(carry % base)
            carry /= base
            index++
        } while (carry > 0 || index < maxOf(values.size, other.values.size))
        return Base(buffer, base)
    }

    fun toString(codex: List<Char>): String {
        require(codex.size == base)
        return values.map { codex[it] }.reversed().joinToString("")
    }

    companion object {
        fun zero(base: Int) = Base(listOf(0), base)
        fun one(base: Int) = Base(listOf(1), base)

        fun fromString(input: String, codex: List<Char>): Base {
            val base = codex.size
            val values = input.map { codex.indexOf(it) }.reversed()
            return Base(values, base)
        }
    }

    object Decimal {
        val CODEX = ('0' .. '9').toList()
        val ZERO = zero(10)
        val ONE = one(10)

        fun fromString(input: String): Base = fromString(input, CODEX)
    }

    object Binary {
        val CODEX = listOf('0', '1')
        val ZERO = zero(2)
        val ONE = one(2)

        fun fromString(input: String): Base = fromString(input, CODEX)
    }

    object Alphabet {
        val CODEX = ('a' .. 'z').toList()
        val ZERO = zero(26)
        val ONE = one(26)

        fun fromString(input: String): Base = fromString(input, CODEX)
    }
}