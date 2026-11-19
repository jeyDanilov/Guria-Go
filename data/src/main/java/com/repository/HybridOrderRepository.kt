package com.repository

import com.model.Order
import com.remote.FirestoreOrderStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HybridOrderRepository @Inject constructor(
    private val store: FirestoreOrderStore,
    private val whatsApp: WhatsAppOrderRepository
) : OrderRepository {

    override suspend fun placeOrder(order: Order): Result<Unit> {
        runCatching { store.save(order) }
        return whatsApp.placeOrder(order)
    }
}