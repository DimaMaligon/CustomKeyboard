package com.example.customkeyboard.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.user_screen),
                    Modifier.padding(115.dp)
                )
            }
        )
    },
        bottomBar = { BottomBar(navHostController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                UserOptions(navController)
            }
        })
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserOptions(navController: NavHostController) {
    Column(
        Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(Screens.AboutApp.route)
            }) {
            Text(text = stringResource(id = R.string.about_app))
        }
    }
}