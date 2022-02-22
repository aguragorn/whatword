package com.aguragorn.whatword.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

fun LocalDate.Companion.today(): LocalDate {
    return Clock.System.todayAt(TimeZone.currentSystemDefault())
}