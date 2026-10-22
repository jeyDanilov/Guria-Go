package com.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuantityStepper(
    quantity: Int,
    min: Int,
    max: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFD9A441))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {onQuantityChange(quantity -1)}, enabled = quantity > min) {
            Text("-")
        }
        Text("$quantity",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 8.dp))

        IconButton(onClick =  { onQuantityChange(quantity + 1) }, enabled = quantity < max) {
            Text("+")
        }
    }
}

@Preview
@Composable
fun QuantityStepperPreview() {
    var quantity by remember { mutableIntStateOf(5) }
    MaterialTheme {
        QuantityStepper(
            quantity = quantity,
            min = 5,
            max = 10,
            onQuantityChange = { quantity = it }
        )
    }
}
