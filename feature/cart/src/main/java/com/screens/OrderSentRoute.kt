package com.screens

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.viewmodel.OrderSentViewModel

@Composable
fun OrderSentRoute(
    onDone: () -> Unit,
    onBackToOrder: () -> Unit,
    viewModel: OrderSentViewModel = hiltViewModel()
) {
    OrderSentScreen(
        onDone = {
            viewModel.clearCart()
            onDone()
        },
        onBackToOrder = onBackToOrder
    )
}