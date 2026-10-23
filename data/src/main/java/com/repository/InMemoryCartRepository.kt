package com.repository

import com.model.CartItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class InMemoryCartRepository @Inject constructor() : CartRepository {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    override val items: StateFlow<List<CartItem>> = _items.asStateFlow()

    override fun add(item: CartItem) {
        _items.update { it + item }
    }

    override fun remove(id: String) {
        _items.update { list -> list.filterNot { it.id == id } }
    }

    override fun clear() {
        _items.value = emptyList()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class CartModule{
        @Binds
        abstract fun bindCartRepository(impl: InMemoryCartRepository): CartRepository
    }
}