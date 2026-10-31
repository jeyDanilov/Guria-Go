package com.model

import java.time.DayOfWeek
import java.time.LocalTime

data class OpeningHours(
    val day: DayOfWeek,
    val open: LocalTime,
    val close: LocalTime
)
