package com.example.customkeyboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.customkeyboard.screens.HomeScreen
import com.example.customkeyboard.screens.UserScreen

fun NavGraphBuilder.bottomNavGraph(navHostController: NavHostController){
    navigation(
        startDestination = Screens.Home.route,
        route = BOTTOM_ROUTE
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(navHostController)
        }
        composable(route = BottomBarScreen.User.route){
            UserScreen(navHostController)
        }
    }

}