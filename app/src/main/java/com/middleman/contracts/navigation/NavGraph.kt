package com.middleman.contracts.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.middleman.contracts.screens.HomeScreen
import com.middleman.contracts.screens.LoginScreen
import com.middleman.contracts.screens.RegisterScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.Login.routes) {
//        composable(Routes.Splash.routes) {
//            Splash(navController)
//        }
//        composable(Routes.Notification.routes) {
//            Notification()
//        }
        composable(Routes.Home.routes) {
            HomeScreen(navController)
        }
//        composable(Routes.Search.routes) {
//            Search()
//        }
//        composable(Routes.Profile.routes) {
//            Profile(navController)
//        }
//        composable(Routes.BottomNav.routes) {
//            BottomNav(navController)
//        }
//        composable(Routes.AddThreads.routes) {
//            AddThreads(navController)
//        }
        composable(Routes.Login.routes) {
            LoginScreen(navController)
        }
        composable(Routes.Register.routes) {
            RegisterScreen(navController)
        }

    }
}