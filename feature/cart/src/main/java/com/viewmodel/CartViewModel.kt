package com.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.CheckoutForm
import com.model.OpenStatus
import com.model.Order
import com.repository.CartRepository
import com.repository.OrderRepository
import com.uistate.CartUiState
import com.usecase.ObserveOpenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: CartRepository,
    private val orders: OrderRepository,
    private val observeOpenStatus: ObserveOpenStatus
) : ViewModel() {
    private val form = MutableStateFlow(CheckoutForm())

    val uiState: StateFlow<CartUiState> =
        combine(cart.items, form, observeOpenStatus()) { items, form, status ->
            CartUiState(items, form, isOpen = status is OpenStatus.Open)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CartUiState(isOpen = observeOpenStatus.current() is OpenStatus.Open)
        )



    fun onFormChange(newForm: CheckoutForm) {
        form.value = newForm
    }

    fun onRemove(id: String) = cart.remove(id)

    fun onSendOrder() {
        val state = uiState.value
        if(!state.canSend) return
        viewModelScope.launch {
            orders.placeOrder(Order(state.items, state.form))
        }
    }
}