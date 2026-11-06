package com.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
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
import com.components.PaymentSwitch
import com.components.SectionCard
import com.components.SummaryRow
import com.model.CartItem
import com.model.CheckoutForm
import com.model.OrderType
import com.theme.GuriaGoTheme
import com.uistate.CartUiState
import androidx.compose.material3.OutlinedButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CartScreen(
    state: CartUiState,
    onFormChange: (CheckoutForm) -> Unit,
    onRemove: (String) -> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    val imeVisible = WindowInsets.isImeVisible
    val isDelivery = state.form.type == OrderType.ENTREGA

    Scaffold(
        modifier = Modifier.imePadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { CartTopBar(onBack) },
        bottomBar = {
            if (state.items.isNotEmpty() && !imeVisible) {
                Surface(shadowElevation = 8.dp, color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.navigationBarsPadding()) {
                        if (!state.isOpen) {
                            Text(
                                "El local está cerrado ahora. Podrás enviar tu pedido cuando abramos.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            )
                        }
                        Text(
                            "Tu nombre, teléfono y dirección se envían al local para preparar tu pedido.",
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
                SectionCard("Tus platos") {
                    state.items.forEachIndexed { index, item ->
                        CartItemRow(item = item, onRemove = { onRemove(item.id) })
                        if (index < state.items.lastIndex) HorizontalDivider()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                        Text("+ Añadir más platos")
                    }
                }

                SectionCard("¿Cómo lo quieres?") {
                    OrderTypeSwitch(
                        selected = state.form.type,
                        onSelected = { type ->
                            onFormChange(
                                state.form.copy(
                                    type = type,
                                    payment = if (type == OrderType.LLEVAR) null else state.form.payment
                                )
                            )
                        }
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

                if (isDelivery) {
                    SectionCard("Dirección de entrega") {
                        GuriaTextField(
                            value = state.form.address,
                            onValueChange = { onFormChange(state.form.copy(address = it)) },
                            label = "Calle y número"
                        )
                        GuriaTextField(
                            value = state.form.details,
                            onValueChange = { onFormChange(state.form.copy(details = it)) },
                            label = "Piso, puerta (opcional)"
                        )
                    }

                    SectionCard("Forma de pago") {
                        PaymentSwitch(
                            selected = state.form.payment,
                            onSelect = { onFormChange(state.form.copy(payment = it)) }
                        )
                        Text(
                            text = if (state.form.payment == null) "Elige cómo vas a pagar al recibir tu pedido."
                            else "Pagas al repartidor cuando recibas tu pedido.",
                            style = MaterialTheme.typography.bodySmall
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (state.hasFreeDelivery) {
                        SummaryRow("Envío", "Gratis")
                    } else if (state.deliveryFeeCents > 0) {
                        SummaryRow("Envío", "%.2f €".format(state.deliveryFeeCents / 100.0))
                        Text(
                            "Añade %.2f € más y el envío es gratis.".format(state.amountToFreeDeliveryCents / 100.0),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    SummaryRow(
                        "Tiempo estimado",
                        "${state.estimate.first}-${state.estimate.last} min"
                    )
                    SummaryRow(
                        "Total",
                        "%.2f €".format(state.totalCents / 100.0),
                        emphasized = true
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
    var form by remember { mutableStateOf(CheckoutForm(type = OrderType.ENTREGA)) }
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

