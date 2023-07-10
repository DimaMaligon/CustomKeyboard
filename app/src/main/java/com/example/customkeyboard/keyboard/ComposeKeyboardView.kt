package com.example.customkeyboard.keyboard

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import com.example.customkeyboard.viewmodel.KeyboardViewModel

class ComposeKeyboardView constructor(
    context: Context, val viewKeyboard: KeyboardViewModel
) : AbstractComposeView(context) {

    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    override fun Content() {
        KeyboardScreen(viewKeyboard)
    }
}