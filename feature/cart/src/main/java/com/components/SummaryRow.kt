package com.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SummaryRow(label: String, value: String, emphasized: Boolean = false){
    val style = if(emphasized) MaterialTheme.typography.titleLarge
    else MaterialTheme.typography.bodyLarge
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        Text(label, style = style)
        Text(
            value,
            style = style,
            fontWeight = FontWeight.Bold,
            color = if (emphasized) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
}