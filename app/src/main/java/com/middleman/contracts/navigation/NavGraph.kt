package com.middleman.contracts.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.middleman.contracts.screens.BottomNav
import com.middleman.contracts.screens.CreateOrder
import com.middleman.contracts.screens.CreatedOrder
import com.middleman.contracts.screens.HomeScreen
import com.middleman.contracts.screens.LoginScreen
import com.middleman.contracts.screens.Notifications
import com.middleman.contracts.screens.Orders
import com.middleman.contracts.screens.Profile
import com.middleman.contracts.screens.RegisterScreen
import com.middleman.contracts.screens.Transactions

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Set the start destination to the BottomNav route
    NavHost(navController = navController, startDestination = Routes.Login.routes) {
        // The BottomNav composable is now the main entry point for navigation
        composable(Routes.BottomNav.routes) {
            BottomNav(navController) // This will contain the bottom navigation bar
        }
        // Define other routes that will be displayed within the BottomNav
        composable(Routes.Orders.routes) {
            Orders(navController) // Orders screen
        }
        composable(Routes.Home.routes) {
            HomeScreen(navController) // Home screen
        }
        composable(Routes.Transactions.routes) {
            Transactions(navController) // Transactions screen
        }
        composable(Routes.Profile.routes) {
            Profile(navController) // Profile screen
        }
        // Login and Register screens are separate and do not include the BottomNav
        composable(Routes.Login.routes) {
            LoginScreen(navController) // Login screen
        }
        composable(Routes.Register.routes) {
            RegisterScreen(navController) // Register screen
        }
        composable(Routes.CreateOrder.routes) {
            CreateOrder(navController)
        }
        composable(Routes.Notifications.routes) {
            Notifications(navController)
        }
//        composable(Routes.CreatedOrder.routes) { backStackEntry ->
//            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
//            CreatedOrder(navController, orderId) // Pass the orderId to CreatedOrder
//        }
    }
}
