package com.example.customkeyboard.keyboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.customkeyboard.viewmodel.KeyboardViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun KeyboardKey(
    keyboardKey: String, viewKeyboard: KeyboardViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val viewmodel = viewKeyboard
    val color by viewmodel.color.collectAsState()
    Box(contentAlignment = Alignment.Center) {
        Text(
            keyboardKey,
            Modifier
                .background(Color(android.graphics.Color.parseColor("#" + color)))
                .border(3.dp, Color.Black)
                .clickable(interactionSource = interactionSource, indication = null) {
                    (context as IMEService).currentInputConnection.commitText(
                        keyboardKey,
                        0
                    )
                }
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )

        )
        if (pressed.value) {
            Text(
                keyboardKey,
                Modifier
                    .border(1.dp, Color.Black)
                    .background(Color.Gray)
                    .padding(
                        start = 13.dp,
                        end = 13.dp,
                        top = 17.dp,
                        bottom = 17.dp
                    )
            )
        }
    }
}

@Composable
fun KeyboardKeyDelete(
    keyboardKey: String, viewKeyboard: KeyboardViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val viewmodel = viewKeyboard
    val color by viewmodel.color.collectAsState()
    Box(contentAlignment = Alignment.Center) {
        Text(
            keyboardKey,
            Modifier
                .background(Color(android.graphics.Color.parseColor("#" + color)))
                .border(3.dp, Color.Black)
                .clickable(interactionSource = interactionSource, indication = null) {
                    (context as IMEService).currentInputConnection.deleteSurroundingText(1, 0)
                }
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )

        )
        if (pressed.value) {
            Text(
                keyboardKey,
                Modifier
                    .border(1.dp, Color.Black)
                    .background(Color.Gray)
                    .padding(
                        start = 13.dp,
                        end = 13.dp,
                        top = 17.dp,
                        bottom = 17.dp
                    )
            )
        }
    }
}