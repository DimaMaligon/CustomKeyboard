package com.example.customkeyboard.navigation


const val BOTTOM_ROUTE = "bottom_bar"
sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object Color: Screens("color_screen")
    object SizeKey: Screens("size_key_screen")
    object FontKey: Screens("font_key_screen")
    object User: Screens("user_screen")
}