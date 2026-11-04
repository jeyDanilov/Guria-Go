package com.uistate

import com.model.CartItem
import com.model.CheckoutForm
import com.model.isValid
import com.model.OrderType
import com.pricing.OrderPolicy
import com.pricing.estimateMinutes

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val form: CheckoutForm = CheckoutForm(),
    val isOpen: Boolean = false
) {
    val itemsCents: Int get() = items.sumOf { it.lineTotalCents }

    val deliveryFeeCents: Int
        get() = if (items.isEmpty()) 0 else OrderPolicy.deliveryFeeCents(form.type, items)

    val totalCents: Int get() = itemsCents + deliveryFeeCents

    val estimate: IntRange get() = estimateMinutes(items, form.type)

    val hasFreeDelivery: Boolean
        get() = form.type == OrderType.ENTREGA && items.isNotEmpty() &&
                itemsCents >= OrderPolicy.FREE_DELIVERY_FROM_CENTS

    val amountToFreeDeliveryCents: Int
        get() = if (form.type == OrderType.ENTREGA && items.isNotEmpty()) {
            (OrderPolicy.FREE_DELIVERY_FROM_CENTS - itemsCents).coerceAtLeast(0)
        } else {
            0
        }

    val canSend: Boolean get() = isOpen && items.isNotEmpty() && form.isValid()
}