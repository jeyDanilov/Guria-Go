package com.repository

import com.model.Order

interface OrderRepository {
    suspend fun placeOrder(order: Order): Result<Unit>
}