package com.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal val  GuriaColorScheme = lightColorScheme(
    primary = Wine,
    onPrimary = Color.White,
    primaryContainer = WineContainer,
    onPrimaryContainer = Wine,
    secondary = Amber,
    onSecondary = Ink,
    background = Cream,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = Sand,
    onSurfaceVariant = Ink,
    outline = Color(0xFFCDBFA9)
)

 internal val  GuriaShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)



