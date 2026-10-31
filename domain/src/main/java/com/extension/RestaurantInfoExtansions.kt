package com.extension

import com.model.OpenStatus
import com.model.RestaurantInfo
import java.time.LocalDateTime

fun RestaurantInfo.statusAt(now: LocalDateTime): OpenStatus {
    val time = now.toLocalTime()
    val current =
        hours.firstOrNull { it.day == now.dayOfWeek && time >= it.open && time < it.close }
    if (current != null) return OpenStatus.Open(current.close)

    for(offset in 0L..7L){
        val date = now.toLocalDate().plusDays(offset)
        val next = hours
            .filter { it.day == date.dayOfWeek }
            .sortedBy { it.open }
            .firstOrNull {offset > 0 || it.open > time}
        if(next != null) return OpenStatus.Closed(date.dayOfWeek, next.open)
    }
    return OpenStatus.Closed(null,null)
}