package com.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.components.CartItemRow
import com.components.CheckoutFormSection
import com.model.CartItem
import com.model.CheckoutForm
import com.model.OrderType
import com.uistate.CartUiState

@Composable
fun CartScreen(
    state: CartUiState,
    onFormChange: (CheckoutForm) -> Unit,
    onRemove: (String) -> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        bottomBar = {
            if (state.items.isNotEmpty()) {
                Button(
                    onClick = onSend,
                    enabled = state.canSend,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B1E2D),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(52.dp)
                ) {
                    Text("Send order · %.2f €".format(state.totalCents / 100.0))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Your order", style = MaterialTheme.typography.headlineMedium)

            if (state.items.isEmpty()) {
                Text("Your cart is empty")
                Button(onClick = onBack) { Text("Back to menu") }
            } else {
                state.items.forEach { item ->
                    CartItemRow(item = item, onRemove = { onRemove(item.id) })
                }
                CheckoutFormSection(form = state.form, onFormChange = onFormChange)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun CartScreenPreview() {
    var form by remember { mutableStateOf(CheckoutForm(type = OrderType.DELIVERY)) }
    val items = listOf(
        CartItem("1", 4, "Wrap", 2, listOf("Chicken", "Spicy"), 2490),
        CartItem("2", 1, "Khinkali", 5, emptyList(), 1250),
    )

    MaterialTheme {
        CartScreen(
            state = CartUiState(items = items, form = form),
            onFormChange = { form = it },
            onRemove = {},
            onSend = {},
            onBack = {}
        )
    }
}
