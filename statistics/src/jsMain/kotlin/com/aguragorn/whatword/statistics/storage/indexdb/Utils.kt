package com.aguragorn.whatword.statistics.storage.indexdb

fun String?.toLongOrZero(): Long = this?.toLongOrNull() ?: 0L
fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0
