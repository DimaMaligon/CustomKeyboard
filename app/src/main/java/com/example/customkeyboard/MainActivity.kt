package com.example.customkeyboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.customkeyboard.viewmodel.KeyboardViewModel
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

val LocalKeyBoardViewModel = compositionLocalOf<KeyboardViewModel> { error("No List Views") }
lateinit var inputMethodManager: InputMethodManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keyboardView : KeyboardViewModel by viewModels()
        inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        setContent {
            CompositionLocalProvider(
                LocalKeyBoardViewModel provides keyboardView,
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        Options()
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                isSystemInDarkTheme()
            }
        }
    }

    fun passData(view : KeyboardViewModel){
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Options() {
    val viewKeyboard = LocalKeyBoardViewModel.current
    val color by viewKeyboard.color.collectAsState()
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        Text(text = "Compose Keyboard")
        val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth().background(Color(android.graphics.Color.parseColor("#" + color))),
            onClick = {
            context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }) {
            Text(text = "Enable IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            inputMethodManager.showInputMethodPicker()
        }) {
            Text(text = "Select IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = text, onValueChange = setValue, modifier = Modifier.fillMaxWidth())

        val controller = rememberColorPickerController()
        HsvColorPicker(
            modifier = Modifier.fillMaxSize(),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                viewKeyboard.setColor(colorEnvelope.hexCode)

                val intent = Intent(context, LifecycleInputMethodService::class.java)
                intent.putExtra("ColorKey", viewKeyboard.color.value)
                context.startService(intent)
            }
        )
    }
}