package com.example.customkeyboard.keyboard

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customkeyboard.R
import com.example.customkeyboard.data.Fonts
import com.example.customkeyboard.viewmodel.KeyboardViewModel


@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun KeyboardKey(
    modifier: Modifier,
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    languageState: MutableState<LanguageState>,
    viewKeyboard: KeyboardViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val keySize by viewKeyboard.keySize.collectAsState()
    val keyFont by viewKeyboard.fontKey.collectAsState()
    val colorKey by viewKeyboard.colorKeys.collectAsState()
    val keyFontObject by remember {
        derivedStateOf {
            findFontObject(keyFont)
        }
    }
    val iconMap = hashMapOf(
        "emoji" to R.drawable.outline_emoji_emotions_24,
        "back" to R.drawable.baseline_arrow_back_24,
        "shift" to R.drawable.shift_outlined,
        "Shift" to R.drawable.shift_filled,
        "SHIFT" to R.drawable.shfit_double_caps,
        "delete" to R.drawable.baseline_backspace_24,
        "enter" to R.drawable.baseline_send,
        "languageRu" to R.drawable.baseline_language_24,
        "languageEn" to R.drawable.baseline_language_24,
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
                        colorKey = colorKey,
                        modifier = modifier,
                        keyboardKey = keyboardKey,
                        drawable = it,
                        keyboardState = keyboardState,
                        languageState = languageState,
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
                    fontFamily = keyFontObject,
                    fontWeight = FontWeight.Bold)
                if (pressed.value) {
                    Text(
                        modifier = modifier
                            .fillMaxSize(),
                        text = keyboardKey,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
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
    colorKey: String,
    modifier: Modifier = Modifier,
    keyboardKey: String,
    drawable: Int,
    keyboardState: MutableState<KeyboardState>,
    languageState: MutableState<LanguageState>,
    textColor: Color = Color.Black,
    viewKeyboard: KeyboardViewModel
) {
    val colorKeyNow by rememberUpdatedState(newValue = colorKey)
    val keyPressed by viewKeyboard.keyPressed.collectAsState()
    val context = LocalContext.current
    Log.d("keypress", "$keyPressed")

    Icon(
        modifier = modifier
            .background(Color(android.graphics.Color.parseColor("#$colorKeyNow")))
            .fillMaxWidth()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        KeyClick(
                            keyboardKey = keyboardKey,
                            keyboardState = keyboardState,
                            languageState = languageState,
                            context = context
                        )
                        Log.d("keypress1", "$keyPressed")
                    }
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

fun KeyClick(
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    languageState: MutableState<LanguageState>,
    context: Context
) {
    val currentInputConnection = (context as IMEService).currentInputConnection

    when (keyboardKey) {
        " " -> {
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE
                )
            )
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.FLAG_LONG_PRESS, KeyEvent.KEYCODE_LANGUAGE_SWITCH
                )
            )
        }

        "Shift" -> {
            keyboardState.value = KeyboardState.DOUBLECAPS
        }

        "emoji" -> {
            keyboardState.value = KeyboardState.EMOJI
        }

        "SHIFT" -> {
            keyboardState.value = KeyboardState.NOCAPS
        }

        "shift" -> {
            keyboardState.value = KeyboardState.CAPS
        }

        "back" -> {
            keyboardState.value = KeyboardState.NOCAPS
        }

        "delete" -> {
            currentInputConnection.deleteSurroundingText(1, 0)
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

        "languageRu" -> languageState.value = LanguageState.RU

        "languageEn" -> languageState.value = LanguageState.EN
    }
}

fun findFontObject(nameFonts: String): FontFamily {
    val listFonts = Fonts.listFonts
    for (font in listFonts) {
        if (nameFonts == font.name) {
            return font.font
        }
    }
    return listFonts[0].font
}