package com.viewmodel

import androidx.lifecycle.ViewModel
import com.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class OrderSentViewModel @Inject constructor(
    private val cart: CartRepository
) : ViewModel() {
    fun clearCart() = cart.clear()
}