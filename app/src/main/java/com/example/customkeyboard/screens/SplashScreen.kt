package com.example.customkeyboard.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.customkeyboard.R
import com.example.customkeyboard.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.popBackStack()
        navController.navigate(Screens.Home.route)
    }
    Splash(alphaAnim.value)
}

@Composable
fun Splash(alphaAnim: Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(200.dp)
                .alpha(alpha = alphaAnim),
            painter = painterResource(id = R.drawable.baseline_keyboard_logo_24),
            contentDescription = "Logo",
            tint = Color.White
        )
    }
}