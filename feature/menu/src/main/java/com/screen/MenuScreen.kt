package com.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.menu.R


data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val image: Int
)

@Composable
fun DishCard(
    dish: Dish,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {

        Image(
            painter = painterResource(dish.image),
            contentDescription = dish.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 3f)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text(dish.name, style = MaterialTheme.typography.titleMedium)
            Text(
                dish.description,
                style = MaterialTheme.typography.bodySmall,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "%.2f €".format(dish.price),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                FilledIconButton(onClick = onAddClick, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "Add ${dish.name}")
                }
            }
        }
    }
}


@Composable
fun MenuScreen(
    categories: List<String>,
    selectedCategory: String,
    dishes: List<Dish>,
    query: String,
    onQueryChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onAddClick: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MenuHeader(query = query, onQueryChange = onQueryChange)

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category) }
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dishes, key = { it.id }) { dish ->
                DishCard(dish = dish, onAddClick = { onAddClick(dish) })
            }
        }
    }
}

@Composable
fun MenuHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF8B1E2D),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    )

    {
        Text(
            "Guria GO",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            "Georgian kitchen",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFD9A441)
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for a dish") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent

            )
        )
    }


}


@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    var query by remember { mutableStateOf("") }
    val allDishes = listOf(
        Dish(
            1,
            "Khinkali",
            "Georgian dumplings with beef and pork",
            25.0,
            R.drawable.khinkali
        ),
        Dish(
            2,
            "Adjarian khachapuri",
            "Cheese bread with egg",
            16.00,
            R.drawable.adjarulikhachapuri
        ),
        Dish(3, "Lobiani", "Bread filled with beans", 9.0, R.drawable.lobiani),
        Dish(4, "Wrap", "Our best-seller", 12.45, R.drawable.wrap),
        Dish(
            5,
            "Imeruli khachapuri",
            "Our best-seller",
            12.0,
            R.drawable.imerulikhachapuri
        ),
        Dish(6, "Badrijani", "Our best-seller", 12.0, R.drawable.badrijani),
        Dish(7, "Ensalada", "Our best-seller", 9.0, R.drawable.ensaladageorgian),
    )

    MaterialTheme {
        MenuScreen(
            categories = listOf(
                "Adjaruli Khachapuri", "Khinkali", "Wrap",
                "Imeruli Khachapuri", "Badrijani", "Ensalada", "Lobiani"
            ),
            selectedCategory = "Adjaruli Khachapuri",
            dishes = allDishes.filter { it.name.contains(query, ignoreCase = true) },
            query = query,
            onQueryChange = { query = it },
            onCategorySelected = {},
            onAddClick = {}
        )
    }
}
