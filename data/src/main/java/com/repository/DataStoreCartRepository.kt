package com.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.datastore.cartDataStore
import com.dto.CartItemDto
import com.mapper.*
import com.model.CartItem
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.collections.emptyList

@Singleton
class DataStoreCartRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : CartRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val key = stringPreferencesKey("items")
    private val json = Json { ignoreUnknownKeys = true }

    override val items: StateFlow<List<CartItem>> = context.cartDataStore.data
        .map { prefs -> decode(prefs[key]) }
        .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override fun add(item: CartItem) = edit { it + item }
    override fun remove(id: String) = edit { list -> list.filterNot { it.id == id } }
    override fun clear() = edit { emptyList() }

    private fun edit(transform: (List<CartItem>) -> List<CartItem>) {
        scope.launch {
            context.cartDataStore.edit { prefs ->
                val updated = transform(decode(prefs[key]))
                prefs[key] = json.encodeToString(updated.map { it.toDto() })
            }
        }
    }

    private fun decode(text: String?): List<CartItem> {
        if (text.isNullOrBlank()) return emptyList()
        return runCatching {
            json.decodeFromString<List<CartItemDto>>(text).map { it.toDomain() }
        }.getOrDefault(emptyList())
    }
}