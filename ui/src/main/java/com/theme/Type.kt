package com.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
fun GuriaGoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GuriaColorScheme,
        shapes = GuriaShapes,
        typography = Typography(),
        content = content
    )
}
