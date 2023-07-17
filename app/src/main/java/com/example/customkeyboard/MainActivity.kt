package com.example.customkeyboard

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.customkeyboard.navigation.NavGraph
import com.example.customkeyboard.ui.theme.AppTheme
import com.example.customkeyboard.viewmodel.KeyboardViewModel

lateinit var inputMethodManager: InputMethodManager

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelKeyboard: KeyboardViewModel by viewModels()

        super.onCreate(savedInstanceState)
        inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        setContent {
            val navController = rememberNavController()
            AppTheme() {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(navController = navController, viewModelKeyboard)
                }
            }
            isSystemInDarkTheme()
        }
    }
}

