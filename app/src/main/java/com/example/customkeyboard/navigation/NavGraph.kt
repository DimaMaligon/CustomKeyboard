package com.example.customkeyboard.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.customkeyboard.screens.AboutAppScreen
import com.example.customkeyboard.screens.ColorScreen
import com.example.customkeyboard.screens.FontScreen
import com.example.customkeyboard.screens.HomeScreen
import com.example.customkeyboard.screens.SizeKeyScreen
import com.example.customkeyboard.screens.SplashScreen
import com.example.customkeyboard.screens.UserScreen
import com.example.customkeyboard.viewmodel.KeyboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController, viewModelKeyboard: KeyboardViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    )
    {
        composable(
            route = Screens.Splash.route
        ) {
            SplashScreen(navController)
        }

        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screens.Color.route
        ) {
            ColorScreen(navController, viewModelKeyboard)
        }

        composable(
            route = Screens.SizeKey.route
        ) {
            SizeKeyScreen(navController, viewModelKeyboard)
        }

        composable(
            route = Screens.FontKey.route
        ) {
            FontScreen(navController, viewModelKeyboard = viewModelKeyboard)
        }

        composable(
            route = Screens.User.route
        ) {
            UserScreen(navController)
        }

        composable(
            route = Screens.AboutApp.route
        ) {
            AboutAppScreen(navController)
        }

        bottomNavGraph(navController)
    }
}