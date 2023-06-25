package com.example.customkeyboard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KeyboardViewModel: ViewModel() {
    private val colorMutable = MutableStateFlow("ffffff")
    val color: StateFlow<String> = colorMutable

    fun setColor(color: String) {
        colorMutable.value = color
    }
}