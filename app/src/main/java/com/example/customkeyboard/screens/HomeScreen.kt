package com.example.customkeyboard.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.inputMethodManager
import com.example.customkeyboard.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    Modifier.padding()
                )
            }
        )
    },
        bottomBar = { BottomBar(navHostController = navController)},
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                TagsMenu(navController = navController)
                Options()
            }
        })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsMenu(navController: NavHostController) {
    val vertScrollState = ScrollState(0)

    FlowRow(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(vertScrollState)
    ) {
        Button(modifier = Modifier.height(35.dp), onClick = {
            navController.navigate(Screens.Color.route)
        }) {
            Text(text = "Цвет")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Options() {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }
    val context = LocalContext.current

    Column(
        Modifier
            .padding(top = 120.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.size(180.dp, 50.dp),
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
                }) {
                Text(text = "Enable IME")
            }
            Button(modifier = Modifier.size(180.dp, 50.dp), onClick = {
                inputMethodManager.showInputMethodPicker()
            }) {
                Text(text = "Select IME")
            }
        }
        Text(text = "Попробуй новую клавиатуру", Modifier.padding(top = 20.dp))
        TextField(
            value = text, onValueChange = setValue,
            modifier = Modifier
                .size(360.dp, 70.dp)
                .padding(top = 20.dp)
        )
    }
}