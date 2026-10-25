package com.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.model.CartItem

@Composable
fun CartItemRow(item: CartItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${item.quantity} * ${item.name}", style = MaterialTheme.typography.titleMedium)
            if (item.optionNames.isNotEmpty()) {
                Text(
                    item.optionNames.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Text("%.2f €".format(item.lineTotalCents / 100.0), fontWeight = FontWeight.Bold)
        TextButton(onClick = onRemove) { Text("Remove") }
    }
}