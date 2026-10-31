package com.model

import java.time.DayOfWeek
import java.time.LocalTime

sealed interface OpenStatus {
    data class Open(val closesAt: LocalTime) : OpenStatus
    data class Closed(val opensDay: DayOfWeek?, val opensAt: LocalTime?) : OpenStatus
}