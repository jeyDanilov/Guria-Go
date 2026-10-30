package com.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mapper.toWhatsAppMessage
import com.model.Order
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class WhatsAppOrderRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : OrderRepository {
    override suspend fun placeOrder(order: Order): Result<Unit> = runCatching {
        val message = order.toWhatsAppMessage()

        val uri = Uri.parse(
            "https://wa.me/$BAR_PHONE?text=${Uri.encode(message)}"
        )
        android.util.Log.d("OrderLink", uri.toString())

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }

    private companion object {
        const val BAR_PHONE = "34629029511"
    }
}