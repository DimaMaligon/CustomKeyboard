package com.example.customkeyboard.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.customkeyboard.navigation.BottomBarScreen

@Composable
fun BottomBar(navHostController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.User
    )
    val navBackStack by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStack?.destination

    BottomAppBar() {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navHostController = navHostController
            )

        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "nav icon")
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navHostController.navigate(screen.route){
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}