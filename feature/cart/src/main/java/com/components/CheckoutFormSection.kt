package com.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.model.CheckoutForm
import com.model.OrderType

@Composable
fun CheckoutFormSection(form: CheckoutForm, onFormChange: (CheckoutForm) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = form.type == OrderType.TAKEAWAY,
                onClick = { onFormChange(form.copy(type = OrderType.TAKEAWAY)) },
                label = { Text("Take away") }
            )
            FilterChip(
                selected = form.type == OrderType.DELIVERY,
                onClick = { onFormChange(form.copy(type = OrderType.DELIVERY)) },
                label = { Text("Delivery") }
            )
        }
        OutlinedTextField(
            value = form.name,
            onValueChange = { onFormChange(form.copy(name = it)) },
            label = { Text("Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.phone,
            onValueChange = { onFormChange(form.copy(phone = it)) },
            label = { Text("Phone") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        if (form.type == OrderType.DELIVERY) {
            OutlinedTextField(
                value = form.address,
                onValueChange = { onFormChange(form.copy(address = it)) },
                label = { Text("Street and number") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.details,
                onValueChange = { onFormChange(form.copy(details = it)) },
                label = { Text("Floor, door (optional)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        OutlinedTextField(
            value = form.notes,
            onValueChange = { onFormChange(form.copy(notes = it)) },
            label = { Text("Note (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}