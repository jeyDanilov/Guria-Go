package com.model

enum class OrderType { ENTREGA, LLEVAR }
enum class PaymentMethod { CASH, CARD }

data class CheckoutForm(
    val type: OrderType = OrderType.LLEVAR,
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val details: String = "",
    val notes: String = "",
    val payment: PaymentMethod? = null
)

fun CheckoutForm.isValid(): Boolean =
    name.isNotBlank() &&
            hasValidPhone() &&
            (type == OrderType.LLEVAR || (address.isNotBlank() && payment != null))

private fun CheckoutForm.hasValidPhone(): Boolean {
    val digits = phone.filter { it.isDigit() }
    return digits.length == 9 || (digits.length == 11 && digits.startsWith("34"))
}