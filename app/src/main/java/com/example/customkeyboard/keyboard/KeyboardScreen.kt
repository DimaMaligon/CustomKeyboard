package com.example.customkeyboard.keyboard

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.customkeyboard.data.KeySize
import com.example.customkeyboard.data_store.KeyboardDataStore
import com.example.customkeyboard.screens.SimpleCircularProgressIndicator
import com.example.customkeyboard.viewmodel.KeyboardViewModel

const val HEIGHT = 60

@SuppressLint("FlowOperatorInvokedInComposition")
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun KeyboardScreen(viewKeyboard: KeyboardViewModel, modifier: Modifier = Modifier) {
    viewKeyboard.apply {
        val context = LocalContext.current
        val keyboardDataStore = KeyboardDataStore(context)
        val showProgress by showProgress.collectAsState()

        LaunchedEffect(key1 = true) {
            keyboardDataStore.getColorKey().collect { data ->
                setColorKeys(data.colorKey)
                onCloseProgress()
            }

            keyboardDataStore.getColorBackGround().collect { data ->
                setColorBackground(data.colorBackground)
                onCloseProgress()
            }

            keyboardDataStore.getFontKey().collect { data ->
                setFontKey(data.fontKey)
                onCloseProgress()
            }

            keyboardDataStore.getSizeKey().collect { data ->
                setKeySize(
                    KeySize(
                        data.startPadding,
                        data.topPadding,
                        data.endPadding,
                        data.bottomPadding
                    )
                )
                onCloseProgress()
            }
        }

        if (showProgress) {
            SimpleCircularProgressIndicator(show = true) {
            }
        } else {
            KeyboardScreenAllLines(viewKeyboard = viewKeyboard, modifier = modifier)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun KeyboardScreenAllLines(viewKeyboard: KeyboardViewModel, modifier: Modifier) {
    val colorBackGround by viewKeyboard.colorBackground.collectAsState()
    val languageState = remember { mutableStateOf(LanguageState.RU) }
    val keyboardState = remember { mutableStateOf(KeyboardState.NOCAPS) }
    val keyboardTemplate = KeyboardTemplates()

    val keysMatrix = when (languageState.value) {
        LanguageState.RU -> keyboardTemplate.getTempleKeyboardRus(keyboardState.value)
        LanguageState.EN -> keyboardTemplate.getTempleKeyboardEn(keyboardState.value)
    }

    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#$colorBackGround")))
            .fillMaxWidth()
    ) {
        keysMatrix.forEach { row ->
            FixedHeightBox(modifier = modifier.fillMaxWidth(), height = HEIGHT.dp) {
                Row(modifier = modifier) {
                    row.forEach { key ->

                        val keyModifier = when (key) {
                            " " -> modifier.weight(3.54f)
                            "enter" -> modifier.weight(2f)
                            else -> modifier.weight(1f)
                        }

                        KeyboardKey(
                            keyboardKey = key,
                            viewKeyboard = viewKeyboard,
                            keyboardState = keyboardState,
                            languageState = languageState,
                            modifier = keyModifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FixedHeightBox(
    modifier: Modifier,
    height: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val heightLayout = height.roundToPx()
        layout(constraints.minWidth, heightLayout) {
            placeables.forEach {
                it.place(
                    x = 0,
                    y = kotlin.math.min(0, heightLayout - it.height)
                )
            }
        }
    }
}