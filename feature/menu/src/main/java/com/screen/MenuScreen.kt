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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.extensions.label
import com.model.OpenStatus
import com.model.OptionGroup
import com.model.Pricing
import com.model.basePriceCents
import com.theme.GuriaGoTheme
import java.time.LocalTime


data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val pricing: Pricing,
    @DrawableRes val image: Int,
    val categories: List<String>,
    val prepMinMinutes: Int = 15,
    val prepMaxMinutes: Int = 20,
    val optionGroups: List<OptionGroup> = emptyList(),
)

@Composable
fun DishCard(
    dish: Dish,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
            Text(
                dish.name, style = MaterialTheme.typography.titleMedium,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

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
                    "%.2f €".format(dish.pricing.basePriceCents() / 100.0),
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
    openStatus: OpenStatus,
    onInfoClick: () -> Unit,
    cartCount: Int,
    cartTotalCents: Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        MenuHeader(
            query = query,
            onQueryChange = onQueryChange,
            onInfoClick = onInfoClick,
            openStatus = openStatus
        )


        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category) })
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
                DishCard(
                    dish = dish,
                    onAddClick = { onAddClick(dish) },
                    onClick = { onAddClick(dish) })
            }
        }
        if (cartCount > 0) {
            Button(
                onClick = onCartClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp)
            ) {
                Text("Ver pedido · ($cartCount) · %.2f €".format(cartTotalCents / 100.0))
            }
        }
    }
}

@Composable
fun MenuHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    openStatus: OpenStatus,
    onInfoClick: () -> Unit,
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
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Guria TO GO",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Cocina georgiana",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFD9A441)
                )
                if (openStatus != null) {
                    Text(
                        openStatus.label(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            IconButton(onClick = onInfoClick) {
                Icon(Icons.Default.Info, contentDescription = "Sobre el bar", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar un plato") },
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
    var selectedCategory by remember { mutableStateOf(FakeDishes.category.first()) }

    val dishes = if (query.isBlank()) {
        FakeDishes.all.filter { selectedCategory in it.categories }
    } else {
        FakeDishes.all.filter { it.name.contains(query, ignoreCase = true) }
    }

    GuriaGoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuScreen(
                categories = FakeDishes.category,
                selectedCategory = selectedCategory,
                dishes = dishes,
                query = query,
                onQueryChange = { query = it },
                onCategorySelected = { selectedCategory = it },
                onAddClick = {},
                openStatus = OpenStatus.Open(LocalTime.of(22, 0)),
                onInfoClick = {},
                cartCount = 2,
                cartTotalCents = 3740,
                onCartClick = {}
            )
        }
    }
}