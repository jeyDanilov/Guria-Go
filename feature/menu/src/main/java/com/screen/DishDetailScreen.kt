package com.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.model.CartItem
import com.model.Option
import com.model.OptionGroup
import com.pricing.calculateTotalCents
import com.pricing.toggle
import com.viewmodel.DishDetailViewModel
import java.util.UUID


@Composable
fun DishDetailScreen(
    dish: Dish,
    quantity: Int,
    selectedOptionsIds: Set<String>,
    onOptionClick: (OptionGroup, Option) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: (CartItem) -> Unit,
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(modifier = Modifier.navigationBarsPadding()) {
                if (!requiredDone) {
                    Text(
                        "Please choose all required options",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Button(
                    onClick = {
                        onAddToCart(
                            CartItem(
                                id = UUID.randomUUID().toString(),
                                dishId = dish.id,
                                name = dish.name,
                                quantity = quantity,
                                optionNames = selectedOptions.map { it.name },
                                lineTotalCents = totalCents
                            )
                        )
                    },
                    enabled = requiredDone,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Wine,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(52.dp)
                ) {
                    Text("Add to cart")
                }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
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
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(dish.image),
            contentDescription = dish.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        )

        IconButton(
            onClick = onBackClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.8f),
                contentColor = Ink
            ),
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ready in about 25 min", style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = "%.2f €".format(totalCents / 100.0),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
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
    onAddedToCart: () -> Unit,
    viewModel: DishDetailViewModel = hiltViewModel()
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
        onAddToCart = { item -> viewModel.addToCart(item)
        onAddedToCart()},
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
    val dish = FakeDishes.byId(4)
    var quantity by remember {mutableIntStateOf(dish.pricing.minQuantity)}
    var selected by remember {mutableStateOf(emptySet<String>())}

    MaterialTheme {
        DishDetailScreen(
            dish = dish,
            quantity = quantity,
            selectedOptionsIds = selected,
            onOptionClick = { group, option -> selected = selected.toggle(group, option)},
            onQuantityChange = {quantity = it},
            onBackClick = {},
            onAddToCart = {}
        )
    }
}

