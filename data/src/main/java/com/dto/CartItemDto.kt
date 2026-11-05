package com.dto

import kotlinx.serialization.Serializable

@Serializable
 data class CartItemDto(
    val id: String,
    val dishId: Int,
    val name: String,
    val quantity: Int,
    val optionNames: List<String>,
    val lineTotalCents: Int,
    val prepMinMinutes: Int = 15,
    val prepMaxMinutes: Int = 20,
    val portions: Int
)
