package com.middleman.contracts.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.middleman.contracts.R
import com.middleman.contracts.model.BottomNavigation
import com.middleman.contracts.navigation.Routes
import com.middleman.contracts.viewmodel.AuthViewModel
import com.middleman.contracts.viewmodel.CreatedOrdersViewModel

@Composable
fun BottomNav(navController: NavHostController) {

    val navController1 = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomBar(navController1) }
    ) { innerPadding ->
        NavHost(navController = navController1, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)) {
            composable(Routes.Orders.routes) {
                val orderViewModel: CreatedOrdersViewModel = viewModel()
                val userId = orderViewModel.getCurrentUserId()
                if (userId != null) {
                    Orders(
                        orderViewModel, userId,
                        navController = navController
                    )
                }
            }
            composable(Routes.Home.routes) {
                HomeScreen(navController)
            }
            composable(Routes.Transactions.routes) {
                Transactions(navController)
            }
            composable(Routes.Profile.routes) {
                Profile(navController)
            }
        }
    }
}



@Composable
fun MyBottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavigation(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Routes.Home.routes
        ),
        BottomNavigation(
            title = "Orders",
            selectedIcon = Icons.Filled.ShoppingCart,
            unSelectedIcon = Icons.Outlined.ShoppingCart,
            route = Routes.Orders.routes
        ),
        BottomNavigation(
            title = "Transactions",
            selectedIcon = ImageVector.vectorResource(R.drawable.transactions_filled),
            unSelectedIcon = ImageVector.vectorResource(R.drawable.transactions_outlined),
            route = Routes.Transactions.routes
        ),
        BottomNavigation(
            title = "Profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            route = Routes.Profile.routes
        )
    )

    BottomAppBar() {
        list.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController1.navigate(it.route) {
                        popUpTo(navController1.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) it.selectedIcon else it.unSelectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }
            )
        }
    }
}
