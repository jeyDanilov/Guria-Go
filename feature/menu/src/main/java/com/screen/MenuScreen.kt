package com.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                .height(110.dp)
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
    onCategorySelected: (String) -> Unit,
    onAddClick: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Text("Guria Go", style = MaterialTheme.typography.headlineMedium)
                Text("Georgian kitchen", style = MaterialTheme.typography.bodyMedium)
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(
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
        }
        items(dishes, key = { it.id }) { dish ->
            DishCard(dish = dish, onAddClick = { onAddClick(dish) })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MaterialTheme {
        MenuScreen(
            categories = listOf(
                "Adjaruli Khachapuri, Khinkali, Wrap," +
                        " Imeruli Khachapuri, Badrijani, Ensalada, Lobiani"
            ),

            selectedCategory = "Adjaruli Khachapuri",
            dishes = listOf(
                Dish(1, "Khinkali", "Georgian dumplings with beef and pork", 25.0, R.drawable.khinkali),
                Dish(2, "Adjarian khachapuri", "Cheese bread with egg", 16.00, R.drawable.adjarulikhachapuri),
                Dish(3, "Lobiani", "Bread filled with beans", 9.0, R.drawable.lobiani),
                Dish(4, "Wrap", "Our best-seller", 12.45, R.drawable.wrap),
                Dish(5, "Imeruli khachapuri", "Our best-seller", 12.0, R.drawable.imerulikhachapuri),
                Dish(6, "Badrijani", "Our best-seller", 12.0, R.drawable.badrijani),
                Dish(7, "Ensalada", "Our best-seller", 9.0, R.drawable.ensaladageorgian),
            ),
            onCategorySelected = {},
            onAddClick = {}
        )
    }
}
