package com.example.customkeyboard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KeyboardViewModel: ViewModel() {
    private val colorKeysMutable = MutableStateFlow("ffffff")
    val colorKeys: StateFlow<String> = colorKeysMutable
    private val colorBackgroundMutable = MutableStateFlow("ffffff")
    val colorBackground: StateFlow<String> = colorBackgroundMutable

    fun setColorKeys(color: String) {
        colorKeysMutable.value = color
    }

    fun setColorBackground(color: String) {
        colorBackgroundMutable.value = color
    }
}