package com.example.customkeyboard.navigation

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
}