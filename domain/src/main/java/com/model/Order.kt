package com.model

data class Order(
    val items: List<CartItem>,
    val form: CheckoutForm
){
    val totalCents: Int get() = items.sumOf { it.lineTotalCents }
}
