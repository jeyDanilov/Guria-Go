package com.model

data class RestaurantInfo(
    val name: String,
    val address: String,
    val phone: String,
    val hours: List<OpeningHours>,
    val pickupNote: String
)


