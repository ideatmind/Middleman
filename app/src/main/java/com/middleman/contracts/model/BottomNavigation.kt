package com.middleman.contracts.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigation(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String // Changed from 'routes' to 'route' for consistency
)