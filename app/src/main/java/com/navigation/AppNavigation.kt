package com.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.screen.DishDetailRoute
import com.screen.InfoRoute
import com.screen.MenuRoute
import com.screens.CartRoute
import kotlinx.serialization.Serializable

@Serializable
data object MenuKey : NavKey

@Serializable
data class DishDetailKey(val dishId: Int) : NavKey

@Serializable
data object CartKey : NavKey

@Serializable
data object InfoKey : NavKey

@Serializable
data object OrderSentKey : NavKey

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(MenuKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),

        entryProvider = entryProvider {
            entry<MenuKey> {
                MenuRoute(
                    onDishClick = { dish -> backStack.add(DishDetailKey(dish.id)) },
                    onCartClick = { backStack.add(CartKey) },
                    onInfoClick = {backStack.add(InfoKey)}
                )
            }
            entry<DishDetailKey> { key ->
                DishDetailRoute(
                    dishId = key.dishId,
                    onBack = { backStack.removeLastOrNull() },
                    onAddedToCart = { backStack.add(CartKey) }
                )
            }
            entry<CartKey> {
                CartRoute(onBack = { backStack.removeLastOrNull() })
            }
            entry<InfoKey> {
                InfoRoute(onBack = { backStack.removeLastOrNull() })
            }
        }
    )
}