package com.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.components.CartItemRow
import com.components.GuriaTextField
import com.components.OrderTypeSwitch
import com.components.SectionCard
import com.model.CartItem
import com.model.CheckoutForm
import com.model.OrderType
import com.theme.GuriaGoTheme
import com.uistate.CartUiState

@Composable
fun CartScreen(
    state: CartUiState,
    onFormChange: (CheckoutForm) -> Unit,
    onRemove: (String) -> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { CartTopBar(onBack) },
        bottomBar = {
            if (state.items.isNotEmpty()) {
                Surface(shadowElevation = 8.dp, color = MaterialTheme.colorScheme.background) {

                    Column(modifier = Modifier.navigationBarsPadding()) {
                        Text(
                            "Tu nombre, teléfono y dirección se envían a la barra para preparar tu pedido. ",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Button(
                            onClick = onSend,
                            enabled = state.canSend,
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(52.dp)
                        ) {
                            Text("Enviar pedido · %.2f €".format(state.totalCents / 100.0))
                        }
                    }
                }
            }
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.items.isEmpty()) {
                EmptyCart(onBack)
            } else {
                SectionCard("Tus artículos") {
                    state.items.forEachIndexed { index, item ->
                        CartItemRow(item = item, onRemove = { onRemove(item.id) })
                        if (index < state.items.lastIndex) HorizontalDivider()
                    }
                }
                SectionCard("¿Cómo lo quieres?") {
                    OrderTypeSwitch(
                        selected = state.form.type,
                        onSelected = { onFormChange(state.form.copy(type = it)) }
                    )
                }
                SectionCard("Tus datos") {
                    GuriaTextField(
                        value = state.form.name,
                        onValueChange = { onFormChange(state.form.copy(name = it)) },
                        label = "Nombre",
                        keyboardOption = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    GuriaTextField(
                        value = state.form.phone,
                        onValueChange = { onFormChange(state.form.copy(phone = it)) },
                        label = "Teléfono",
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }
                if (state.form.type == OrderType.DELIVERY) {
                    SectionCard("Dirección de entrega") {
                        GuriaTextField(
                            value = state.form.address,
                            onValueChange = { onFormChange(state.form.copy(address = it)) },
                            label = "Calle y número"
                        )
                        GuriaTextField(
                            value = state.form.details,
                            onValueChange = { onFormChange(state.form.copy(details = it)) },
                            label = "Suelo, puerta (opcional)"
                        )
                    }
                }
                SectionCard("Nota para la cocina") {
                    GuriaTextField(
                        value = state.form.notes,
                        onValueChange = { onFormChange(state.form.copy(notes = it)) },
                        label = "Opcional",
                        singleLine = false
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "%.2f €".format(state.totalCents / 100.0),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun CartTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            "Tu pedido",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EmptyCart(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onBack) { Text("Volver al menú") }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun CartScreenPreview() {
    var form by remember { mutableStateOf(CheckoutForm(type = OrderType.DELIVERY)) }
    val items = listOf(
        CartItem("1", 4, "Wrap", 2, listOf("Pollo", "Picante"), 2490),
        CartItem("2", 1, "Khinkali", 5, emptyList(), 1250),
    )

    GuriaGoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CartScreen(
                state = CartUiState(items = items, form = form),
                onFormChange = { form = it },
                onRemove = {},
                onSend = {},
                onBack = {}
            )
        }
    }
}

