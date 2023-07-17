package com.example.customkeyboard.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.customkeyboard.navigation.BottomBarScreen
import com.example.customkeyboard.navigation.Screens
import com.example.customkeyboard.ui.theme.shapes

@Composable
fun BottomBar(navHostController: NavHostController) {
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
            navHostController.navigate(screen.route) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsMenu(navController: NavHostController) {
    val vertScrollState = ScrollState(0)

    FlowRow(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(vertScrollState)
            .padding(top = 15.dp)
    ) {
        Button(modifier = Modifier.height(35.dp), onClick = {
            navController.navigate(Screens.Color.route)
        }) {
            Text(text = "Цвет")
        }
        Button(modifier = Modifier.height(35.dp), onClick = {
            navController.navigate(Screens.SizeKey.route)
        }) {
            Text(text = "Размер клавиш")
        }
        Button(modifier = Modifier.height(35.dp), onClick = {
            navController.navigate(Screens.FontKey.route)
        }) {
            Text(text = "Вид шрифта")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSizeKey(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = shapes.medium,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun SimpleCircularProgressIndicator(
    show: Boolean,
    onConfirm: () -> Unit
) {
    if (show) {
        CircularProgressIndicator(
            Modifier
                .fillMaxSize()
                .padding(top = 400.dp),
            strokeWidth = 5.dp,

            )
    }
}