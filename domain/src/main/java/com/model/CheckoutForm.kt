package com.model

enum class OrderType { ENTREGA, LLEVAR }

data class CheckoutForm(
    val type: OrderType = OrderType.LLEVAR,
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val details: String = "",
    val notes: String = ""
)

fun CheckoutForm.isValid(): Boolean =
    name.isNotBlank() &&
            phone.count { it.isDigit() } == 9 &&
            (type == OrderType.LLEVAR || address.isNotBlank())