package com.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.components.SectionCard
import com.model.OpeningHours
import com.model.RestaurantInfo
import com.theme.GuriaGoTheme
import com.viewmodel.InfoViewMode
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun InfoRoute(
    onBack: () -> Unit,
    viewModel: InfoViewMode = hiltViewModel()
) {
    val context = LocalContext.current
    val info = viewModel.info

    InfoScreen(
        info = info,
        onBack = onBack,
        onCall = {
            runCatching {
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info.phone}")))
            }
        },
        onOpenMaps = {
            runCatching {
                var uri = Uri.parse("geo:0,0?q=${Uri.encode(info.address)}")
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    )
}


@Composable
fun InfoScreen(
    info: RestaurantInfo,
    onBack: () -> Unit,
    onCall: () -> Unit,
    onOpenMaps: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    info.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard("Dirección") {
                Text(info.address)
                OutlinedButton(onClick = onOpenMaps) { Text("Abierto en mapas") }
            }
            SectionCard("Teléfono") {
                Text(info.phone)
                OutlinedButton(onClick = onCall) { Text("Llamar") }
            }
            SectionCard("Horario de apertura") {
                DayOfWeek.entries.forEach { day ->
                    val slots = info.hours.filter { it.day == day }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(day.getDisplayName(TextStyle.FULL, Locale.getDefault()))
                        Text(
                            if (slots.isEmpty()) "Cerrado"
                            else slots.joinToString { "${it.open} - ${it.close}" }
                        )
                    }
                }
            }
            SectionCard("Levantar") {
                Text(info.pickupNote)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun InfoScreenPreview(){
    val info = RestaurantInfo(
        name = "Guria To Go",
        address = "Calle de Castañares de Rioja 4",
        phone ="+34629029511",
        hours = DayOfWeek.entries
            .filter { it != DayOfWeek.TUESDAY }
            .map { OpeningHours(it, LocalTime.of(9,0), LocalTime.of(22,0)) },
        pickupNote = "Recoge tu pedido en la barra."
    )
    GuriaGoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            InfoScreen(info = info, onBack = {}, onCall = {}, onOpenMaps = {})
        }
    }
}