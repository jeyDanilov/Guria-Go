package com.extensions

import com.model.OpenStatus
import java.time.format.TextStyle
import java.util.Locale


fun OpenStatus.label(): String = when (this) {
    is OpenStatus.Open -> "Abierto ahora · hasta $closesAt"
    is OpenStatus.Closed -> {
        val day = opensDay
        val time = opensAt
        if (day == null || time == null) {
            "Cerrado"
        } else {
            "Cerrado · abre ${day.getDisplayName(TextStyle.FULL, Locale.getDefault())} en $time"
        }
    }
}