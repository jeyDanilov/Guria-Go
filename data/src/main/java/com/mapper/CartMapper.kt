package com.mapper

import com.dto.CartItemDto
import com.model.CartItem

fun CartItem.toDto() = CartItemDto(
    id = id,
    dishId = dishId,
    name = name,
    quantity = quantity,
    optionNames = optionNames,
    lineTotalCents = lineTotalCents,
    prepMinMinutes = prepMinMinutes,
    prepMaxMinutes = prepMaxMinutes,
    portions = portions
)

fun CartItemDto.toDomain() = CartItem(
    id = id,
    dishId = dishId,
    name = name,
    quantity = quantity,
    optionNames = optionNames,
    lineTotalCents = lineTotalCents,
    prepMinMinutes = prepMinMinutes,
    prepMaxMinutes = prepMaxMinutes,
    portions = portions
)