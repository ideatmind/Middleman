package com.middleman.contracts.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.viewmodel.AuthViewModel

@Composable
fun Profile(navController : NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)
    Column {
        Text("Profile")

        LaunchedEffect(firebaseUser) {
            if(firebaseUser == null) {
                navController.navigate(Routes.Login.routes) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }

        Button(onClick = {
            authViewModel.logout()
        }) {
            Text("Logout")
        }
    }
}