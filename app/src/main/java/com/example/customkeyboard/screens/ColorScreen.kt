package com.example.customkeyboard.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.data_store.ColorBackgroundData
import com.example.customkeyboard.data_store.ColorKeyData
import com.example.customkeyboard.data_store.KeyboardDataStore
import com.example.customkeyboard.keyboard.IMEService
import com.example.customkeyboard.viewmodel.KeyboardViewModel
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreen(navController: NavHostController, viewModelKeyboard: KeyboardViewModel) {
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.screen_color),
                    Modifier.padding(55.dp)
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back_icon))
                }
            },
            actions = {
                IconButton(onClick = {
                    openDialog = !openDialog
                }) {
                    Icon(Icons.Filled.Info, "info icon")
                }
            }
        )
    },
        bottomBar = { BottomBar(navHostController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                TagsMenu(navController = navController)
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PopupWindowDialog(
                        openDialog = openDialog,
                        stringResource(id = R.string.text_color_info)
                    )
                }
                MainElements(viewModelKeyboard)
            }
        })
}

@Composable
fun MainElements(viewModelKeyboard: KeyboardViewModel) {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("Напечатай тут")) }

    Column(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title_try_it),
            Modifier.padding(top = 20.dp),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium
        )
        TextField(
            value = text,
            onValueChange = setValue,
            modifier = Modifier
                .size(360.dp, 90.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )
        ColorPickerCircle(viewModelKeyboard)
    }
}

@Composable
fun ColorPickerCircle(viewModelKeyboard: KeyboardViewModel) {
    val context = LocalContext.current
    val controller = rememberColorPickerController()
    var checkedStateKeyboardColor by remember { mutableStateOf(true) }
    var checkedStateBackgroundColor by remember { mutableStateOf(false) }
    val colorKey by viewModelKeyboard.colorKeys.collectAsState()
    val colorBackground by viewModelKeyboard.colorBackground.collectAsState()
    val keyboardDataStore = KeyboardDataStore(context)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val scope = rememberCoroutineScope()

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.color_keyboard))
                Checkbox(checked = checkedStateKeyboardColor, onCheckedChange = {
                    checkedStateKeyboardColor = it
                })
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.color_background_keyboard))
                Checkbox(checked = checkedStateBackgroundColor, onCheckedChange = {
                    checkedStateBackgroundColor = it
                })
            }
        }
        Button(
            onClick = {
                scope.launch {
                    keyboardDataStore.saveColorKey(
                        ColorKeyData(
                            colorKey = colorKey
                        )
                    )
                    keyboardDataStore.saveBackgroundKey(
                        ColorBackgroundData(
                            colorBackground = colorBackground
                        )
                    )
                }
            }, Modifier.width(350.dp)
        ) {
            Text(
                stringResource(id = R.string.button_save),
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    HsvColorPicker(modifier = Modifier
        .size(400.dp, 400.dp)
        .padding(top = 20.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            if (checkedStateKeyboardColor) {
                val intent = Intent(context, IMEService::class.java)
                intent.putExtra("ColorKey", colorEnvelope.hexCode)
                context.startService(intent)
                viewModelKeyboard.setColorKeys(colorEnvelope.hexCode)
            }
            if (checkedStateBackgroundColor) {
                val intent = Intent(context, IMEService::class.java)
                intent.putExtra("ColorBackground", colorEnvelope.hexCode)
                context.startService(intent)
                viewModelKeyboard.setColorBackground(colorEnvelope.hexCode)
            }
        })
}