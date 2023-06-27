package com.example.customkeyboard.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
)
{
    object Home: BottomBarScreen(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )
    object User: BottomBarScreen(
        route = "login_screen",
        title = "User",
        icon = Icons.Default.AccountBox
    )
}