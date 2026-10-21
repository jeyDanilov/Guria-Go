package com.pricing

import com.model.Option
import com.model.OptionGroup
import com.model.Pricing
import com.model.SelectionType
import com.model.basePriceCents

fun calculateTotalCents(pricing: Pricing, quantity: Int, selected: List<Option>): Int {
    val unit = pricing.basePriceCents()
    return (unit + selected.sumOf { it.extraPriceCents }) * quantity
}

fun Set<String>.toggle(group: OptionGroup, option: Option): Set<String> = when (group.type) {
    SelectionType.SINGLE -> this - group.options.map { it.id }.toSet() + option.id
    SelectionType.MULTIPLE -> if (option.id in this) this - option.id else this + option.id
}