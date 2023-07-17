package com.example.customkeyboard.data_store

data class ColorKeyData(
    val colorKey: String
)

data class ColorBackgroundData(
    val colorBackground: String
)

data class FontKeyData(
    val fontKey: String
)

data class SizeKeyData(
    var startPadding: Int,
    var topPadding: Int,
    var endPadding: Int,
    var bottomPadding: Int
)