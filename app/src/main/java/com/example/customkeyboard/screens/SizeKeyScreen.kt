package com.example.customkeyboard.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.keyboard.IMEService
import com.example.customkeyboard.viewmodel.KeyboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeKeyScreen(
    navController: NavHostController, viewKeyboard: KeyboardViewModel
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(id = R.string.app_name), Modifier.padding()
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        })
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TagsMenu(navController = navController)
            OptionSizeKey(viewKeyboard = viewKeyboard)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionSizeKey(viewKeyboard: KeyboardViewModel) {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParametersKey(viewKeyboard = viewKeyboard)

        Text(text = "Попробуй новую клавиатуру", Modifier.padding(top = 20.dp))
        TextField(
            value = text,
            onValueChange = setValue,
            modifier = Modifier
                .size(360.dp, 90.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersKey(viewKeyboard: KeyboardViewModel) {
    viewKeyboard.apply {
        val maxChar = 2
        var keySizeLeft by remember { mutableStateOf(TextFieldValue("10")) }
        var keySizeTop by remember { mutableStateOf(TextFieldValue("10")) }
        var keySizeRight by remember { mutableStateOf(TextFieldValue("10")) }
        var keySizeBottom by remember { mutableStateOf(TextFieldValue("10")) }

        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier.height(250.dp), verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "лево", Modifier.padding(end = 10.dp)
                    )
                    TextFieldSizeKey(
                        value = keySizeLeft, onValueChange = { newText ->
                            val digits = newText.text.filter {
                                it.isDigit()
                            }
                            if (newText.text.length <= maxChar) {
                                keySizeLeft =
                                    TextFieldValue(text = digits, selection = newText.selection)
                            }
                        }, modifier = Modifier
                            .size(70.dp, 60.dp)
                            .padding(end = 10.dp)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "верх", Modifier.padding(bottom = 10.dp)
                )
                TextFieldSizeKey(
                    value = keySizeTop, onValueChange = { newText ->
                        val digits = newText.text.filter {
                            it.isDigit()
                        }
                        if (newText.text.length <= maxChar) {
                            keySizeTop =
                                TextFieldValue(text = digits, selection = newText.selection)
                        }
                    }, modifier = Modifier
                        .size(60.dp, 70.dp)
                        .padding(bottom = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .size(60.dp, 50.dp)
                        .padding(
                            0.dp, 0.dp, 0.dp, 0.dp
                        ),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Card() {
                        Text(
                            modifier = Modifier
                                .background(Color.Gray)
                                .size(50.dp, 50.dp)
                                .padding(top = 12.dp),
                            text = "A",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                TextFieldSizeKey(
                    value = keySizeBottom, onValueChange = { newText ->
                        val digits = newText.text.filter {
                            it.isDigit()
                        }
                        if (newText.text.length <= maxChar) {
                            keySizeBottom =
                                TextFieldValue(text = digits, selection = newText.selection)
                        }
                    }, modifier = Modifier
                        .size(60.dp, 80.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                )
                Text(
                    text = "низ", Modifier.padding(bottom = 10.dp)
                )
            }
            Column(
                Modifier.height(250.dp), verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldSizeKey(
                        value = keySizeRight, onValueChange = { newText ->
                            val digits = newText.text.filter {
                                it.isDigit()
                            }
                            if (newText.text.length <= maxChar) {
                                keySizeRight =
                                    TextFieldValue(text = digits, selection = newText.selection)
                            }
                        }, modifier = Modifier
                            .size(70.dp, 60.dp)
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = "право", Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
        Row() {
            SaveKeyObject(
                left = keySizeLeft, top = keySizeTop, bottom = keySizeBottom, right = keySizeRight
            )
        }
    }
}

@Composable
fun SaveKeyObject(
    left: TextFieldValue, top: TextFieldValue, bottom: TextFieldValue, right: TextFieldValue
) {
    val context = LocalContext.current
    Button(onClick = {
        val newLeft = left.text.toInt()
        val newTop = top.text.toInt()
        val newBottom = bottom.text.toInt()
        val newRight = right.text.toInt()
        val listKeySize = arrayListOf(newLeft, newTop, newBottom, newRight)

        val intent = Intent(context, IMEService::class.java)
        intent.putExtra("SizeKey", listKeySize)
        context.startService(intent)
    }) {
        Text(
            "Применить настройки"
        )
    }
}


