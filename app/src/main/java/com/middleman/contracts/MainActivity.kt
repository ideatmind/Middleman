package com.middleman.contracts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.middleman.contracts.navigation.NavGraph
import com.middleman.contracts.ui.theme.ContractsTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.White.toArgb(), Color.DarkGray.toArgb()))
        installSplashScreen()
        setContent {
            ContractsTheme {
                    val navController = rememberNavController()
                    NavGraph(navController)
            }
        }
    }
}