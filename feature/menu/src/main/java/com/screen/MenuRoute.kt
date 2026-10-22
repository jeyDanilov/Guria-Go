package com.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MenuRoute(onDishClick: (Dish) -> Unit) {
    var query by remember { mutableStateOf("") }
    MenuScreen(
        categories = listOf("Adjaruli Khachapuri", "Khinkali", "Wrap", "Imeruli Khachapuri", "Badrijani", "Ensalada", "Lobiani"),
        selectedCategory = "Adjaruli Khachapuri",
        dishes = FakeDishes.all.filter { it.name.contains(query, ignoreCase = true) },
        query = query,
        onQueryChange = { query = it },
        onCategorySelected = {},
        onAddClick = onDishClick
    )
}