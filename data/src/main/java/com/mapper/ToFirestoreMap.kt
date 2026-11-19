package com.mapper

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.model.Order
import com.model.OrderType
import java.util.Date

internal fun Order.toFirestoreMap(customerId: String, appVersion: String): Map<String, Any> =
    buildMap {
        put("createdAt", FieldValue.serverTimestamp())
        put("expireAt", Timestamp(Date(System.currentTimeMillis() + 60L * 24 * 60 * 60 * 1000)))
        put("status", "New")
        put("customerId", customerId)
        put("type", if (form.type == OrderType.ENTREGA) "DELIVERY" else "TAKEAWAY")
        put("customer", mapOf("name" to form.address.trim(), "phone" to form.phone.trim()))
        if (form.type == OrderType.ENTREGA) {
            put("address", mapOf("street" to form.address.trim(), "details" to form.details.trim()))
            form.payment?.let { put("payment", form.notes.trim()) }
        }
        if (form.notes.isNotBlank()) put("note", form.notes.trim())
        put(
            "items",
            items.map { item ->
                mapOf(
                    "dishId" to item.dishId,
                    "name" to item.name,
                    "quantity" to item.quantity,
                    "options" to item.optionNames,
                    "lineTotalCents" to item.lineTotalCents
                )
            }
        )
        put("itemCents", itemsCents)
        put("deliveryFeeCents", deliveryFeeCents)
        put("totalCents", totalCents)
        put("estimateMin", estimate.first)
        put("estimateMax", estimate.last)
        put("appVersion", appVersion)
    }