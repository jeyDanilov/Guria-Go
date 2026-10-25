package com.model

enum class OrderType { DELIVERY, TAKEAWAY }

data class CheckoutForm(
    val type: OrderType = OrderType.TAKEAWAY,
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val details: String = "",
    val notes: String = ""
)

fun CheckoutForm.isValid(): Boolean =
    name.isNotBlank() &&
            phone.count { it.isDigit() } == 9 &&
            (type == OrderType.TAKEAWAY || address.isNotBlank())