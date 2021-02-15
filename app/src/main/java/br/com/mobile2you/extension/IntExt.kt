package br.com.mobile2you.extension

import java.util.*

private val suffixes = TreeMap<Long, String>().apply {
    put(1_000L, "k")
    put(1_000_000L, "M")
    put(1_000_000_000L, "G")
    put(1_000_000_000_000L, "T")
    put(1_000_000_000_000_000L, "P")
    put(1_000_000_000_000_000_000L, "E")
}

fun Int.formatAsMultipleOfHundread(): String {
    if (this == Integer.MIN_VALUE) return (Integer.MIN_VALUE + 1).formatAsMultipleOfHundread()
    if (this < 0) return "-" + this.formatAsMultipleOfHundread()
    if (this < 1000) return this.toString() // deal with easy case
    val e = suffixes.floorEntry(this.toLong())
    val divideBy = e.key
    val suffix = e.value
    val truncated = this / (divideBy / 10) // the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}