package com.example.customkeyboard.keyboard

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.customkeyboard.viewmodel.KeyboardViewModel

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun KeyboardScreen(viewKeyboard: KeyboardViewModel) {
    val colorBackGround by viewKeyboard.colorBackground.collectAsState()
    val keysMatrix = arrayOf(
        arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        arrayOf("Z", "X", "C", "V", "B", "N", "M")
    )
    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#" + colorBackGround)))
            .fillMaxWidth()
    ) {
        keysMatrix.forEach { row ->
            FixedHeightBox(modifier = Modifier.fillMaxWidth()) {
                Row(Modifier) {
                    if(row.contains("Z")){
                        KeyboardKeyDelete(keyboardKey = "<-", viewKeyboard = viewKeyboard)
                    }
                    row.forEach { key ->
                        KeyboardKey(keyboardKey = key, viewKeyboard = viewKeyboard)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun FixedHeightBox(modifier: Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val childConstraints = Constraints(
            minWidth = constraints.minWidth / 4,
            minHeight = constraints.minHeight,
            maxWidth = if (constraints.hasBoundedWidth) {
                constraints.maxWidth
            } else {
                Constraints.Infinity
            },
            maxHeight = if (constraints.hasBoundedHeight) {
                constraints.maxHeight
            } else {
                Constraints.Infinity
            }
        )

        val placeables = measurables.map { measurable ->
            measurable.measure(childConstraints)
        }

        val layoutWidth = (placeables.minByOrNull { it.width }?.width ?: 0)
        val layoutHeight = (placeables.maxByOrNull { it.height }?.height ?: 0)
        layout(layoutWidth, layoutHeight) {
            placeables.forEach {
                it.placeRelative(layoutWidth - it.width, layoutHeight - it.height)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun KeyboardKey(
    keyboardKey: String, viewKeyboard: KeyboardViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val color by viewKeyboard.colorKeys.collectAsState()
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