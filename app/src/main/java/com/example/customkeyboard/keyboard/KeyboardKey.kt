package com.example.customkeyboard.keyboard

import android.content.Context
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputConnection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customkeyboard.R
import com.example.customkeyboard.viewmodel.KeyboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeyboardKey(
    modifier: Modifier,
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    viewKeyboard: KeyboardViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val colorKey by viewKeyboard.colorKeys.collectAsState()
    val keySize by viewKeyboard.keySize.collectAsState()
    val iconMap = hashMapOf(
        "emoji" to R.drawable.outline_emoji_emotions_24,
        "shift" to R.drawable.shift_outlined,
        "Shift" to R.drawable.shift_filled,
        "SHIFT" to R.drawable.shfit_double_caps,
        "delete" to R.drawable.baseline_backspace_24,
        "enter" to R.drawable.baseline_send,
        " " to R.drawable.spacebar_filled,
        "123" to R.drawable.baseline_123,
        "ABC" to R.drawable.baseline_abc,
    )
    Box(
        modifier = modifier
            .size(10.dp, 60.dp)
            .padding(keySize.start.dp, keySize.top.dp, keySize.end.dp, keySize.bottom.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Card() {
            if (iconMap.containsKey(keyboardKey)) {
                iconMap[keyboardKey]?.let {
                    KeyboardIcon(
                        context = context,
                        modifier = modifier,
                        keyboardKey = keyboardKey,
                        drawable = it,
                        keyboardState = keyboardState,
                        viewKeyboard = viewKeyboard
                    )
                }
            } else {
                Text(modifier = modifier
                    .background(Color(android.graphics.Color.parseColor("#$colorKey")))
                    .fillMaxSize()
                    .padding(2.dp)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        (context as IMEService).currentInputConnection.commitText(
                            keyboardKey, keyboardKey.length
                        )
                    }
                    .padding(
                        top = 14.dp
                    ),
                    text = keyboardKey,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
                if (pressed.value) {
                    Text(
                        modifier = modifier
                            .size(50.dp, 80.dp),
                        text = keyboardKey,
                        textAlign = TextAlign.Center,
                        color = Color.Green,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardIcon(
    context: Context,
    modifier: Modifier = Modifier,
    keyboardKey: String,
    drawable: Int,
    keyboardState: MutableState<KeyboardState>,
    textColor: Color = Color.Black,
    viewKeyboard: KeyboardViewModel
) {
    val colorKey by viewKeyboard.colorKeys.collectAsState()
    val currentInputConnection = (context as IMEService).currentInputConnection
    Icon(modifier = modifier
        .background(Color(android.graphics.Color.parseColor("#$colorKey")))
        .fillMaxWidth()
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> whenKeyClick(
                    motionEvent = it,
                    keyboardKey = keyboardKey,
                    keyboardState = keyboardState,
                    currentInputConnection = currentInputConnection
                )
            }
            true
        }
        .padding(2.dp)
        .padding(
            top = 14.dp, bottom = 14.dp
        ),
        painter = painterResource(id = drawable),
        contentDescription = stringResource(R.string.key_icon),
        tint = textColor)
}


fun whenKeyClick(
    motionEvent: MotionEvent,
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    currentInputConnection: InputConnection
) {
    when (keyboardKey) {
        " " -> {
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE
                )
            )
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_LANGUAGE_SWITCH
                )
            )
        }

        "Shift" -> {
            keyboardState.value = KeyboardState.DOUBLECAPS
        }

        "SHIFT" -> {
            keyboardState.value = KeyboardState.NOCAPS
        }

        "shift" -> {
            keyboardState.value = KeyboardState.CAPS
        }

        "delete" -> {
            CoroutineScope(Dispatchers.IO).launch {
                while (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    delay(100)
                    currentInputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL
                        )
                    )
                }
            }
        }

        "enter" -> {
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER
                )
            )
        }

        "123" -> keyboardState.value = KeyboardState.NUMBER
        "ABC" -> keyboardState.value = KeyboardState.STRING
    }
}