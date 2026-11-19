package com.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viewmodel.CartViewModel

@Composable
fun CartRoute(
    onBack: () -> Unit,
    onOrderSent: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CartViewModel.CartEvent.OrderSent -> onOrderSent()
                is CartViewModel.CartEvent.SendFailed -> {}
            }
        }
    }

    CartScreen(
        state = state,
        onFormChange = viewModel::onFormChange,
        onRemove = viewModel::onRemove,
        onSend = viewModel::onSendOrder,
        onBack = onBack
    )
}