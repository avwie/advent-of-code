package nl.avwie.aoc.common

import java.security.MessageDigest

private val md5 = MessageDigest.getInstance("MD5")

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
fun String.md5(): ByteArray = md5.digest(this.toByteArray())

fun <T> List<T>.permutations(): List<List<T>> {
    if (this.size == 1) return listOf(this)

    val perms = mutableListOf<List<T>>()
    val toInsert = this[0]
    this.drop(1).permutations().forEach { perm ->
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}