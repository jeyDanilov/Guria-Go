package com.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.CheckoutForm
import com.repository.CartRepository
import com.uistate.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: CartRepository
) : ViewModel() {
    private val form = MutableStateFlow(CheckoutForm())

    val uiState: StateFlow<CartUiState> =
        combine(cart.items, form) { items, form ->
            CartUiState(items, form)
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartUiState())

    fun onFormChange(newForm: CheckoutForm) {
        form.value = newForm
    }

    fun onRemove(id: String) = cart.remove(id)

    fun onSendOrder() {
        // next step: send the order by WhatsApp
    }

}