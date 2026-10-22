package com.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.model.Option
import com.model.OptionGroup
import com.pricing.calculateTotalCents
import com.pricing.toggle


private val Wine = Color(0xFF8B1E2D)

@Composable
fun DishDetailScreen(
    dish: Dish,
    quantity: Int,
    selectedOptionsIds: Set<String>,
    onOptionClick: (OptionGroup, Option) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: () -> Unit,
    onBackClick: () -> Unit
) {

    val selectedOptions = dish.optionGroups
        .flatMap { it.options }
        .filter { it.id in selectedOptionsIds }

    val totalCents = calculateTotalCents(
        dish.pricing,
        quantity,
        selectedOptions
    )

    val requiredDone = dish.optionGroups
        .filter { it.required }
        .all { group ->
            group.options.any { it.id in selectedOptionsIds }
        }

    Scaffold(
        bottomBar = {
            Button(
                onClick = onAddToCart,
                enabled = requiredDone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Add to cart")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            HeaderImage(
                dish = dish,
                onBackClick = onBackClick
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                QuantityStepper(
                    quantity = quantity,
                    min = dish.pricing.minQuantity,
                    max = dish.pricing.maxQuantity,
                    onQuantityChange = onQuantityChange
                )
            }

            DishInfo(
                dish = dish,
                totalCents = totalCents
            )

            dish.optionGroups.forEach { group ->
                OptionGroupSection(
                    group = group,
                    selectedIds = selectedOptionsIds,

                    onOptionClick = {
                        onOptionClick(group, it)
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(35.dp)
            )
        }
    }
}

@Composable
fun HeaderImage(
    dish: Dish,
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Image(
            painter = painterResource(dish.image),
            contentDescription = dish.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
        )
    }
}

@Composable
fun DishInfo(
    dish: Dish,
    totalCents: Int
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Ready in about 25 min"
                )
            }

            Text(
                text = "%.2f €".format(totalCents / 100.0),
                color = Wine
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = dish.description
        )
    }
}

@Composable
fun DishDetailRoute(
    dishId: Int,
    onBack: () -> Unit,
    onAddedToCart: () -> Unit
) {
    val dish = remember(dishId) {
        FakeDishes.byId(dishId)
    }

    var quantity by remember {
        mutableIntStateOf(dish.pricing.minQuantity)
    }

    var selectedOptionsIds by remember {
        mutableStateOf(emptySet<String>())
    }

    DishDetailScreen(
        dish = dish,
        quantity = quantity,
        selectedOptionsIds = selectedOptionsIds,
        onQuantityChange = {
            quantity = it
        },
        onOptionClick = { group, option ->
            selectedOptionsIds =
                selectedOptionsIds.toggle(group, option)
        },
        onAddToCart = onAddedToCart,
        onBackClick = onBack
    )
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 780
)
@Composable
fun DishDetailPreview() {
    MaterialTheme {
        DishDetailRoute(
            dishId = 4,
            onBack = {},
            onAddedToCart = {}
        )
    }
}

