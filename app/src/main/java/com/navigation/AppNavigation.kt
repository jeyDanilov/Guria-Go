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
import com.screens.OrderSentRoute
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

    // never removes the last screen
    val goBack: () -> Unit = {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }
    // does not open the same screen twice (double tap)
    val navigateTo: (NavKey) -> Unit = { key ->
        if (backStack.lastOrNull() != key) backStack.add(key)
    }

    NavDisplay(
        backStack = backStack,
        onBack = goBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<MenuKey> {
                MenuRoute(
                    onDishClick = { dish -> navigateTo(DishDetailKey(dish.id)) },
                    onCartClick = { navigateTo(CartKey) },
                    onInfoClick = { navigateTo(InfoKey) }
                )
            }
            entry<DishDetailKey> { key ->
                DishDetailRoute(
                    dishId = key.dishId,
                    onBack = goBack,
                    onAddedToCart = goBack
                )
            }
            entry<CartKey> {
                CartRoute(
                    onBack = goBack,
                    onOrderSent = { navigateTo(OrderSentKey) }
                )
            }
            entry<InfoKey> {
                InfoRoute(onBack = goBack)
            }
            entry<OrderSentKey> {
                OrderSentRoute(
                    onDone = {
                        // keep only the first screen (the menu)
                        while (backStack.size > 1) backStack.removeLastOrNull()
                    },
                    onBackToOrder = goBack
                )
            }
        }
    )
}