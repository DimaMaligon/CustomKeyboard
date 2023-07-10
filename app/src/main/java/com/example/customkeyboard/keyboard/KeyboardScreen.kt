package com.example.customkeyboard.keyboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customkeyboard.viewmodel.KeyboardViewModel

const val HEIGHT = 60

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun KeyboardScreen(viewKeyboard: KeyboardViewModel, modifier: Modifier = Modifier) {
    val colorBackGround by viewKeyboard.colorBackground.collectAsState()
    val keyboardState = remember { mutableStateOf(KeyboardState.CAPS) }

    val keysMatrix = when (keyboardState.value) {
        KeyboardState.CAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", ",", " ", ".", "enter")
        )

        KeyboardState.NOCAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
            arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            arrayOf("shift", "z", "x", "c", "v", "b", "n", "m", "delete"),
            arrayOf("123", "emoji", ",", " ", ".", "enter")
        )

        KeyboardState.DOUBLECAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("SHIFT", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", ",", " ", ".", "enter")
        )

        KeyboardState.NUMBER -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
            arrayOf("=", "*", "\"", "'", ":", ";", "!", "?", "delete"),
            arrayOf("ABC", ",", "_", " ", "/", ".", "enter")
        )

        KeyboardState.EMOJI -> arrayOf(
            arrayOf("back","😁","😂","😃","😆","😇","😈","😉","😊","😋","😌","😍","😎"),
            arrayOf("😓","😔","😖","😘","😚","😜","😝","😞","😠","😡","😢","😣","😤"),
            arrayOf("😥","😨","😩","😪","😫","😭","😰","😐","😒","😱","😲","😳","😵"),
            arrayOf("😶","😷","😸","😹","😺","😻","😼","😽","😾","😿","🙀","🙅","😏"),
            arrayOf("🙇","🙈","🙉","🙊","🙋","🙌","🙍","🙎","✋","✋","🐲","👀","🐝"),
        )

        else -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", ",", " ", ".", "enter")
        )
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