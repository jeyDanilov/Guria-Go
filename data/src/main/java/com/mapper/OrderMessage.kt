package com.mapper

import com.model.Order
import com.model.OrderType

fun Order.toWhatsAppMessage(): String = buildString {
    appendLine("Nuevo pedido - Guria Go")
    appendLine(if(form.type == OrderType.DELIVERY) "Tipo: A domicilio" else "Tipo: Para recoger")
    appendLine()
    items.forEach { item ->
        val options = if(item.optionNames.isEmpty()) "" else "(${item.optionNames.joinToString(", ")})"
        appendLine(". ${item.quantity} * ${item.name}$options - %.2f €".format(item.lineTotalCents / 100.0))
    }
    appendLine()
    appendLine("Total: %.2f €".format(totalCents / 100.0))
    appendLine()
    appendLine("Nombre: ${form.name}")
    appendLine("Teléfono: ${form.phone}")
    if(form.type == OrderType.DELIVERY) {
        appendLine("Dirección: ${form.address} ${form.details}".trim())
    }
    if(form.notes.isNotBlank()) appendLine("Nota: ${form.notes}")
}