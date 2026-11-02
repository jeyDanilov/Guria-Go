package com.extensions

import com.model.OpenStatus
import java.time.format.TextStyle
import java.util.Locale


fun OpenStatus.label(): String = when (this) {
    is OpenStatus.Open -> "Open now · until $closesAt"
    is OpenStatus.Closed -> {
        val day = opensDay
        val time = opensAt
        if (day == null || time == null) {
            "Closed"
        } else {
            "Closed · opens ${day.getDisplayName(TextStyle.FULL, Locale.getDefault())} at $time"
        }
    }
}