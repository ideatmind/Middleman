package com.middleman.contracts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BlinkingDot() {
    var alpha by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            // Fade out
            alpha = 1f
            delay(500)
            alpha = 0f
            delay(500)
        }
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .background(Color.Green.copy(alpha = alpha), CircleShape)
    )
}