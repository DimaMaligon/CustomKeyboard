package com.example.customkeyboard.screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.data.Fonts
import com.example.customkeyboard.keyboard.IMEService

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ), title = {
            Text(
                text = stringResource(id = R.string.screen_font), Modifier.padding(55.dp)
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back_icon))
            }
        })
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TagsMenu(navController = navController)
            AllElements()
        }
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllElements() {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }

    Column(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FontList()

        Text(text = stringResource(id = R.string.title_try_it), Modifier.padding(top = 20.dp))
        TextField(
            value = text,
            onValueChange = setValue,
            modifier = Modifier
                .size(360.dp, 90.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FontList() {
    val fontState = remember {
        mutableStateOf("")
    }
    val fontsList = remember {
        Fonts.listFonts
    }
    val context = LocalContext.current

    Column() {
        Text(
            text = stringResource(id = R.string.second_title_font),
            Modifier.padding(top = 10.dp),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleLarge,
        )
        LazyColumn(
            modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(fontsList) {
                Text(text = it.name, modifier = Modifier
                    .clickable {
                        fontState.value = it.name
                    }
                    .background(
                        if (fontState.value == it.name) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else MaterialTheme.colorScheme.background
                    )
                    .padding(vertical = 10.dp))
            }
        }
        Button(onClick = {
            val intent = Intent(context, IMEService::class.java)
            intent.putExtra("FontKey", fontState.value)
            context.startService(intent)
        }) {
            Text(
                stringResource(id = R.string.button_save)
            )
        }
    }
}