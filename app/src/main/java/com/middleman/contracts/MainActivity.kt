package com.middleman.contracts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.middleman.contracts.navigation.NavGraph
import com.middleman.contracts.screens.LoginScreen
import com.middleman.contracts.screens.RegisterScreen
import com.middleman.contracts.ui.theme.ContractsTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContractsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
//                    shape = {}
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController)
//                    RegisterScreen(navController)
                }
            }
        }
    }
}