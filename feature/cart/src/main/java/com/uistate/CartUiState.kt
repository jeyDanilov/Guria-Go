package com.uistate

import com.model.CartItem
import com.model.CheckoutForm
import com.model.isValid

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val form: CheckoutForm = CheckoutForm(),
) {
    val totalCents: Int get() = items.sumOf { it.lineTotalCents }
    val canSend: Boolean get() = items.isNotEmpty() && form.isValid()
}