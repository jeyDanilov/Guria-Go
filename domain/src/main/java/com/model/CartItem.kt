package com.model

data class CartItem(
    val id: String,
    val dishId: Int,
    val name: String,
    val quantity: Int,
    val optionNames: List<String>,
    val lineTotalCents: Int
)
