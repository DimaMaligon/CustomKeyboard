package com.example.customkeyboard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.customkeyboard.data.KeySize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KeyboardViewModel: ViewModel() {
    private val fontKeyMutable = MutableStateFlow("fontPlayfair")
    val fontKey: StateFlow<String> = fontKeyMutable
    private val colorKeysMutable = MutableStateFlow("ffffff")
    val colorKeys: StateFlow<String> = colorKeysMutable
    private val colorBackgroundMutable = MutableStateFlow("ffffff")
    val colorBackground: StateFlow<String> = colorBackgroundMutable
    private val keySizeMutable = MutableStateFlow(KeySize(0, 0, 0, 0))
    val keySize: StateFlow<KeySize> = keySizeMutable

    fun setFontKey(font: String?) {
        fontKeyMutable.value = font.toString()
    }
    fun setColorKeys(color: String?) {
        colorKeysMutable.value = color.toString()
    }

    fun setColorBackground(color: String?) {
        colorBackgroundMutable.value = color.toString()
    }

    fun setKeySize(keySize: KeySize) {
        keySizeMutable.value = keySize
    }
}