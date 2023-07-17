package com.example.customkeyboard.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


const val KEYBOARD_DATASTORE = "keyboard_datastore"
private val Context.datastore by preferencesDataStore(name = KEYBOARD_DATASTORE)

class KeyboardDataStore(val context: Context) {

    companion object {
        val COLOR_KEY = stringPreferencesKey("COLOR_KEY")
        val COLOR_BACKGROUND = stringPreferencesKey("COLOR_BACKGROUND")
        val FONT_KEY = stringPreferencesKey("FONT_KEY")
        val START_PADDING = intPreferencesKey("START_PADDING")
        val TOP_PADDING = intPreferencesKey("TOP_PADDING")
        val END_PADDING = intPreferencesKey("END_PADDING")
        val BOTTOM_PADDING = intPreferencesKey("BOTTOM_PADDING")
    }

    suspend fun saveColorKey(colorKeyData: ColorKeyData) {
        context.datastore.edit { preference ->
            preference[COLOR_KEY] = colorKeyData.colorKey
        }
    }

    suspend fun saveBackgroundKey(colorBackgroundData: ColorBackgroundData) {
        context.datastore.edit { preference ->
            preference[COLOR_BACKGROUND] = colorBackgroundData.colorBackground
        }
    }

    suspend fun saveFontKey(keyFontData: FontKeyData) {
        context.datastore.edit { preference ->
            preference[FONT_KEY] = keyFontData.fontKey
        }
    }

    suspend fun saveSizeKey(sizeKeyData: SizeKeyData) {
        context.datastore.edit { preference ->
            preference[START_PADDING] = sizeKeyData.startPadding
            preference[TOP_PADDING] = sizeKeyData.topPadding
            preference[END_PADDING] = sizeKeyData.endPadding
            preference[BOTTOM_PADDING] = sizeKeyData.bottomPadding
        }
    }

    fun getColorKey() = context.datastore.data.map { preference ->
        return@map ColorKeyData(
            preference[COLOR_KEY] ?: "ffffff"
        )
    }

    fun getColorBackGround() = context.datastore.data.map { preference ->
        return@map ColorBackgroundData(
            preference[COLOR_BACKGROUND] ?: "ffffff"
        )
    }

    fun getFontKey() = context.datastore.data.map { preference ->
        return@map FontKeyData(
            preference[FONT_KEY] ?: "fontPlayfair"
        )
    }

    fun getSizeKey() =
        context.datastore.data.map { preference ->
            return@map SizeKeyData(
                preference[START_PADDING] ?: 0,
                preference[TOP_PADDING] ?: 0,
                preference[END_PADDING] ?: 0,
                preference[BOTTOM_PADDING] ?: 0,
            )
        }
}