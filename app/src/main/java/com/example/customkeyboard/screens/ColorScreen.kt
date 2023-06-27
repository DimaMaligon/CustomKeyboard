package com.example.customkeyboard.screens

import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.customkeyboard.IMEService
import com.example.customkeyboard.R
import com.example.customkeyboard.inputMethodManager
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    Modifier.padding()
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            }
        )
    },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                ColorPicker()
            }
        })
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker() {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }
    val context = LocalContext.current
    val vertScrollState = ScrollState(0)

    FlowRow(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(vertScrollState)
    ) {
        Button(modifier = Modifier.height(35.dp), onClick = {
            inputMethodManager.showInputMethodPicker()
        }) {
            Text(text = "Цвет")
        }
    }

    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Попробуй новую клавиатуру", Modifier.padding(top = 20.dp))
        TextField(
            value = text, onValueChange = setValue,
            modifier = Modifier
                .size(360.dp, 70.dp)
                .padding(top = 20.dp)
        )

        val controller = rememberColorPickerController()
        HsvColorPicker(
            modifier = Modifier
                .size(400.dp, 400.dp)
                .padding(top = 20.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                val intent = Intent(context, IMEService::class.java)
                intent.putExtra("ColorKey", colorEnvelope.hexCode)
                context.startService(intent)
            }
        )
    }
}