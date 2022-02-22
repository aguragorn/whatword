package com.aguragorn.whatword.game.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

fun LocalDate.asPuzzleNumber(): Int {
    val dayZero = LocalDate(2022, 2, 15)
    return minus(dayZero).days
}