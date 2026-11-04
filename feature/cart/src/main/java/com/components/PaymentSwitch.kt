package com.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.model.PaymentMethod

@Composable
fun PaymentSwitch(
    selected: PaymentMethod?,
    onSelect: (PaymentMethod) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(50))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(4.dp)
    ){
        PaymentMethod.entries.forEach { method ->
            val isSelected = method == selected
            Box(modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50))
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable{onSelect(method)}
                .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = if(method == PaymentMethod.CASH) "Efectivo" else "Tarjeta",
                    fontWeight = FontWeight.SemiBold,
                    color = if(isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}