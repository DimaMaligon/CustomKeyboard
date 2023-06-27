package com.example.customkeyboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.customkeyboard.screens.ColorScreen
import com.example.customkeyboard.screens.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
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

        bottomNavGraph(navController)
    }
}