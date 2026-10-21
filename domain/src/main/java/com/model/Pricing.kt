package com.model

sealed interface Pricing {
    val minQuantity: Int
    val maxQuantity: Int


    data class Fixed(
        val priceCents: Int,
        override val minQuantity: Int = 1,
        override val maxQuantity: Int = 10
    ) : Pricing

    data class PerUnit(
        val unitPriceCents: Int,
        override val minQuantity: Int,
        override val maxQuantity: Int
    ) : Pricing
}
fun Pricing.basePriceCents(): Int = when (this) {
    is Pricing.Fixed -> priceCents
    is Pricing.PerUnit -> unitPriceCents
}



