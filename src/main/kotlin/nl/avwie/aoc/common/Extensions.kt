package nl.avwie.aoc.common

import java.security.MessageDigest

private val md5 = MessageDigest.getInstance("MD5")

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
fun String.md5(): ByteArray = md5.digest(this.toByteArray())