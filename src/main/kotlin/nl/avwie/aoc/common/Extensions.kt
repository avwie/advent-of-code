package nl.avwie.aoc.common

import java.security.MessageDigest

private val md5 = MessageDigest.getInstance("MD5")

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
fun String.md5(): ByteArray = md5.digest(this.toByteArray())

fun Long.toDigits() : List<Int> {
    val digits = mutableListOf<Int>()
    var remaining = this
    while (remaining > 0) {
        digits.add((remaining % 10).toInt())
        remaining /= 10L
    }
    return digits.reversed()
}

fun List<Int>.toLong() : Long = this.fold(0L) { acc, d ->
    acc * 10 + d
}

fun List<Int>.toInt() : Int = this.fold(0) { acc, d ->
    acc * 10 + d
}

fun <T> T.reduceRepeated(n: Int, block: (T) -> T): T = (0 until n).fold(this) { acc, _ -> block(acc) }