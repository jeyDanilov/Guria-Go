package com.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.components.SectionCard
import com.extensions.label
import com.model.OpenStatus
import com.model.OpeningHours
import com.model.RestaurantInfo
import com.theme.GuriaGoTheme
import com.viewmodel.InfoViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

private val SpanishLocale: Locale = Locale.forLanguageTag("es-ES")

@Composable
fun InfoRoute(
    onBack: () -> Unit,
    viewModel: InfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val info = viewModel.info
    val openStatus by viewModel.openStatus.collectAsStateWithLifecycle()

    InfoScreen(
        info = info,
        openStatus = openStatus,
        onBack = onBack,
        onCall = {
            runCatching {
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info.phone}")))
            }
        },
        onOpenMaps = {
            runCatching {
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(info.address)}")
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    )
}

@Composable
fun InfoScreen(
    info: RestaurantInfo,
    openStatus: OpenStatus?,
    onBack: () -> Unit,
    onCall: () -> Unit,
    onOpenMaps: () -> Unit
) {
    val today = remember { LocalDate.now(ZoneId.of("Europe/Madrid")).dayOfWeek }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                )
                .statusBarsPadding()
                .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 24.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = info.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (openStatus != null) {
                    Text(
                        text = openStatus.label(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard("Dónde estamos") {
                ContactRow(
                    icon = Icons.Default.Place,
                    text = info.address,
                    action = "Cómo llegar",
                    onAction = onOpenMaps
                )
                HorizontalDivider()
                ContactRow(
                    icon = Icons.Default.Phone,
                    text = info.phone,
                    action = "Llamar",
                    onAction = onCall
                )
            }

            SectionCard("Horario") {
                DayOfWeek.entries.forEach { day ->
                    val slots = info.hours.filter { it.day == day }
                    val isToday = day == today
                    val color = if (isToday) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                    val weight = if (isToday) FontWeight.Bold else FontWeight.Normal

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = day.getDisplayName(TextStyle.FULL, SpanishLocale)
                                .replaceFirstChar { it.uppercase() },
                            color = color,
                            fontWeight = weight
                        )
                        Text(
                            text = if (slots.isEmpty()) "Cerrado"
                            else slots.joinToString { "${it.open} - ${it.close}" },
                            color = color,
                            fontWeight = weight
                        )
                    }
                }
            }

            SectionCard("Recogida") {
                Text(info.pickupNote)
            }
        }
    }
}

@Composable
private fun ContactRow(
    icon: ImageVector,
    text: String,
    action: String,
    onAction: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        )
        FilledTonalButton(onClick = onAction) { Text(action) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun InfoScreenPreview() {
    val info = RestaurantInfo(
        name = "Guria TO GO",
        address = "Santo Domingo de la Calzada",
        phone = "+34629029511",
        hours = DayOfWeek.entries
            .filter { it != DayOfWeek.TUESDAY }
            .map { OpeningHours(it, LocalTime.of(9, 0), LocalTime.of(22, 0)) },
        pickupNote = "Recoge tu pedido en la barra. Estará listo en unos 20 minutos."
    )
    GuriaGoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            InfoScreen(
                info = info,
                openStatus = OpenStatus.Open(LocalTime.of(22, 0)),
                onBack = {},
                onCall = {},
                onOpenMaps = {}
            )
        }
    }
}