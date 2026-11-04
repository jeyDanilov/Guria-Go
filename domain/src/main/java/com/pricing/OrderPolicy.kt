package com.pricing

import com.model.CartItem
import com.model.OrderType

object OrderPolicy {
    const val DELIVERY_FEE_CENTS = 300                 // 3,00 €
    const val FREE_DELIVERY_FROM_CENTS = 3000          // free from 30,00 €
    const val DELIVERY_EXTRA_MINUTES = 15
    const val EXTRA_MINUTES_PER_EXTRA_DISH = 5
    const val MAX_EXTRA_MINUTES = 20

    fun deliveryFeeCents(type: OrderType, itemsCents: Int): Int =
        if (type == OrderType.ENTREGA && itemsCents < FREE_DELIVERY_FROM_CENTS) {
            DELIVERY_FEE_CENTS
        } else {
            0
        }

    fun deliveryFeeCents(type: OrderType, items: List<CartItem>): Int =
        deliveryFeeCents(type, items.sumOf { it.lineTotalCents })
}
fun estimateMinutes(items: List<CartItem>, type: OrderType): IntRange {
    if (items.isEmpty()) return 0..0
    val extra = ((items.size - 1) * OrderPolicy.EXTRA_MINUTES_PER_EXTRA_DISH)
        .coerceAtMost(OrderPolicy.MAX_EXTRA_MINUTES)
    val delivery = if (type == OrderType.ENTREGA) OrderPolicy.DELIVERY_EXTRA_MINUTES else 0
    val min = items.maxOf { it.prepMinMinutes } + extra + delivery
    val max = items.maxOf { it.prepMaxMinutes } + extra + delivery
    return min..max
}