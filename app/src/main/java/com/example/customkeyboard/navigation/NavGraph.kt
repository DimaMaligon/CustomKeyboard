package com.example.customkeyboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.customkeyboard.screens.ColorScreen
import com.example.customkeyboard.screens.FontScreen
import com.example.customkeyboard.screens.HomeScreen
import com.example.customkeyboard.screens.SizeKeyScreen
import com.example.customkeyboard.viewmodel.KeyboardViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModelKeyboard: KeyboardViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    )
    {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screens.Color.route
        ) {
            ColorScreen(navController)
        }

        composable(
            route = Screens.SizeKey.route
        ) {
            SizeKeyScreen(navController)
        }
        composable(
            route = Screens.FontKey.route
        ) {
            FontScreen(navController)
        }

        bottomNavGraph(navController)
    }
}