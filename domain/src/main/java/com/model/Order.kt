package com.model

import com.pricing.OrderPolicy
import com.pricing.estimateMinutes

data class Order(
    val items: List<CartItem>,
    val form: CheckoutForm
){
    val totalCents: Int get() = items.sumOf { it.lineTotalCents }
    val deliveryFeeCents: Int get() = OrderPolicy.deliveryFeeCents(form.type, items)
    val itemsCents: Int get() = items.sumOf { it.lineTotalCents }
    val estimate: IntRange get() = estimateMinutes(items, form.type)

}
