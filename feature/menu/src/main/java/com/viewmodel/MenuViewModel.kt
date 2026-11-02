package com.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.OpenStatus
import com.repository.CartRepository
import com.usecase.ObserveOpenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class CartSummary(val count: Int = 0, val totalCents: Int = 0)

@HiltViewModel
class MenuViewModel @Inject constructor(
    cart: CartRepository,
    observeOpenStatus: ObserveOpenStatus
) : ViewModel() {

    val cartSummary: StateFlow<CartSummary> = cart.items
        .map { items -> CartSummary(items.size, items.sumOf { it.lineTotalCents }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartSummary())

    val openStatus: StateFlow<OpenStatus?> = observeOpenStatus()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}