package com.viewmodel

import androidx.lifecycle.ViewModel
import com.model.CartItem
import com.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class DishDetailViewModel @Inject constructor(
    private val cart: CartRepository
) : ViewModel() {

    fun addToCart(dish: CartItem) {
        cart.add(dish)
    }
}