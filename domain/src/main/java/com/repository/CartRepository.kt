package com.repository

import com.model.CartItem
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val items: StateFlow<List<CartItem>>
    fun add(item: CartItem)
    fun remove(id: String)
    fun clear()
}