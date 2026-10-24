package com.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MenuRoute(onDishClick: (Dish) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(FakeDishes.category.first()) }

    val dishes = if (query.isBlank()) {
        FakeDishes.all.filter { selectedCategory in it.categories }
    }else{
        FakeDishes.all.filter { it.name.contains(query, ignoreCase = true) }
    }

    MenuScreen(
        categories = FakeDishes.category,
        selectedCategory = selectedCategory,
        dishes = dishes,
        query = query,
        onQueryChange = { query = it },
        onCategorySelected = {},
        onAddClick = onDishClick
    )
}