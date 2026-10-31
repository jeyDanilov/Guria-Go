package com.repository

import com.model.OpeningHours
import com.model.RestaurantInfo
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRestaurantRepository @Inject constructor(): RestaurantRepository {
    override val info = RestaurantInfo(
        name = "Guria To Go",
        address = "Avenida Ingenieros del Mopu, 7",
        phone = "+34629029511",
        hours = DayOfWeek.entries
            .filter { it != DayOfWeek.THURSDAY }
            .map { OpeningHours (it, LocalTime.of(9,0), LocalTime.of(22,0))},
        pickupNote = "Recoge tu pedido en la barra. Estará listo en unos 20 minutos."
    )
}